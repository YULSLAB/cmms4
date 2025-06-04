package com.cmms4.inventoryMaster.service;

import com.cmms4.inventoryMaster.entity.InventoryMaster;
import com.cmms4.inventoryMaster.entity.InventoryMasterIdClass;
import com.cmms4.inventoryMaster.repository.InventoryMasterRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<InventoryMaster> getInventoryMasterByInventoryId(String companyId, Integer inventoryId) {
        return inventoryMasterRepository.findByCompanyIdAndInventoryIdAndDeleteMarkIsNull(companyId, inventoryId);
    }

    @Transactional
    public InventoryMaster saveInventoryMaster(InventoryMaster inventoryMaster, String username) {
        LocalDateTime now = LocalDateTime.now();
        Integer maxInventoryId = inventoryMasterRepository.findMaxInventoryIdByCompanyId(inventoryMaster.getCompanyId());
        int newInventoryId = (maxInventoryId == null) ? 2000000000 : maxInventoryId + 1;
        inventoryMaster.setInventoryId(newInventoryId); 
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

} 