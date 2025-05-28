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
    private static final String NON_DELETED_MARKER = "N";
    private static final String DELETED_MARKER = "Y";

    public InventoryMasterService(InventoryMasterRepository inventoryMasterRepository) {
        this.inventoryMasterRepository = inventoryMasterRepository;
    }

    @Transactional(readOnly = true)
    public Page<InventoryMaster> getAllInventoryMasters(String companyId, String siteId, Pageable pageable) {
        return inventoryMasterRepository.findByCompanyIdAndSiteIdAndDeleteMarkIsNull(companyId, siteId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<InventoryMaster> getInventoryMasterById(String companyId, String inventoryId) {
        return inventoryMasterRepository.findByCompanyIdAndInventoryIdAndDeleteMarkIsNull(companyId, inventoryId);
    }

    @Transactional
    public InventoryMaster saveInventoryMaster(InventoryMaster inventoryMaster) {
        if (inventoryMaster.getCreateDate() == null) {
            inventoryMaster.setCreateDate(LocalDateTime.now());
            inventoryMaster.setDeleteMark(NON_DELETED_MARKER);
        }
        inventoryMaster.setUpdateDate(LocalDateTime.now());
        return inventoryMasterRepository.save(inventoryMaster);
    }

    @Transactional
    public void softDeleteInventoryMaster(String companyId, String inventoryId) {
        InventoryMasterIdClass id = new InventoryMasterIdClass(companyId, inventoryId);
        InventoryMaster inventoryMaster = inventoryMasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("InventoryMaster not found with id: " + id));

        if (!DELETED_MARKER.equals(inventoryMaster.getDeleteMark())) {
            inventoryMaster.setDeleteMark(DELETED_MARKER);
            inventoryMaster.setUpdateDate(LocalDateTime.now());
            inventoryMasterRepository.save(inventoryMaster);
        }
    }
} 