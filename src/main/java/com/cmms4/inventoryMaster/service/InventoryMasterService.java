package com.cmms4.inventoryMaster.service;

import com.cmms4.inventoryMaster.entity.InventoryHistory;
import com.cmms4.inventoryMaster.entity.InventoryMaster;
import com.cmms4.inventoryMaster.entity.InventoryMasterIdClass;
import com.cmms4.inventoryMaster.repository.InventoryMasterRepository;
import com.cmms4.inventoryMaster.repository.InventoryHistoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * cmms4 - InventoryService
 * 재고 관리 서비스
 * 
 * @author cmms4
 * @since 2024-03-19
 */

@Service
public class InventoryMasterService {

    private final InventoryMasterRepository inventoryMasterRepository;

    public InventoryMasterService(InventoryMasterRepository inventoryMasterRepository) {
        this.inventoryMasterRepository = inventoryMasterRepository;
    }

    @Transactional(readOnly = true)
    public Page<InventoryMaster> getAllInventoryMasters(String companyId, String siteId, Pageable pageable) {
        return inventoryMasterRepository.findByCompanyIdAndSiteIdAndDeleteMarkIsNull(companyId, siteId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<InventoryMaster> getInventoryMasterByInventoryId(String companyId, String inventoryId) {
        return inventoryMasterRepository.findByCompanyIdAndInventoryIdAndDeleteMarkIsNull(companyId, inventoryId);
    }

    @Transactional
    public InventoryMaster saveInventoryMaster(InventoryMaster inventoryMaster, String username) {
        LocalDateTime now = LocalDateTime.now();
        String maxInventoryId = inventoryMasterRepository.findMaxInventoryIdByCompanyId(inventoryMaster.getCompanyId());
        int newInventoryId = (maxInventoryId == null) ? 2000000000 : Integer.parseInt(maxInventoryId) + 1;
        inventoryMaster.setInventoryId(String.valueOf(newInventoryId));
        inventoryMaster.setCreateDate(now);
        inventoryMaster.setCreateBy(username);
        
        return inventoryMasterRepository.save(inventoryMaster);

    }

    @Transactional
    public void deleteInventoryMaster(String companyId, String inventoryId) {
        InventoryMasterIdClass id = new InventoryMasterIdClass(companyId, inventoryId);
        if (!inventoryMasterRepository.existsById(id)) {
            throw new RuntimeException("InventoryMaster not found with id: " + id);
        }
        inventoryMasterRepository.deleteById(id);
    }

    @Transactional
    public void processInventoryIo(List<InventoryHistory> ioList, String username) {
        for (InventoryHistory dto : ioList) {
            
            InventoryMaster inv = inventoryMasterRepository
                .findByCompanyIdAndInventoryIdForUpdate(dto.getCompanyId(), dto.getInventoryId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

            BigDecimal qtyChange = "I".equals(dto.getIoType()) ? dto.getQty() : dto.getQty().negate();
            BigDecimal newQty = inv.getCurrentQty().add(qtyChange);

            if (newQty.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("Insufficient stock for " + dto.getInventoryId());
            }

            inv.setCurrentQty(newQty);

            BigDecimal valueChange = "I".equals(dto.getIoType()) ? dto.getTotalValue() : dto.getTotalValue().negate();
            inv.setCurrentValue(inv.getCurrentValue().add(valueChange));

            inventoryMasterRepository.save(inv);

            dto.setIoDate(LocalDateTime.now());
            dto.setCreateDate(LocalDateTime.now());
            dto.setCreateBy(username);
            dto.setHistoryId(generateHistoryId());

            inventoryHistoryRepository.save(dto);
        }
    }

} 