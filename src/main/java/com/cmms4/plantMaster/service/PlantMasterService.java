package com.cmms4.plantMaster.service;

import com.cmms4.plantMaster.entity.PlantMaster;
import com.cmms4.plantMaster.entity.PlantMasterIdClass;
import com.cmms4.plantMaster.repository.PlantMasterRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * cmms4 - PlantService
 * 설비 관리 서비스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Service
public class PlantMasterService {

    private final PlantMasterRepository plantMasterRepository;

      
    public PlantMasterService(PlantMasterRepository plantMasterRepository) {
        this.plantMasterRepository = plantMasterRepository;
    }

    @Transactional(readOnly = true)
    public Page<PlantMaster> getAllPlantMasters(String companyId, String siteId, Pageable pageable) {
        return plantMasterRepository.findByCompanyIdAndSiteIdAndDeleteMarkIsNull(companyId, siteId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<PlantMaster> getPlantMasterById(String companyId, String plantId) {
        return plantMasterRepository.findByCompanyIdAndPlantIdAndDeleteMarkIsNull(companyId, plantId);
    }

    @Transactional
    public PlantMaster savePlantMaster(PlantMaster plantMaster) {
        if (plantMaster.getCreateDate() == null) {
            plantMaster.setCreateDate(LocalDateTime.now());
        }
        plantMaster.setUpdateDate(LocalDateTime.now());
        return plantMasterRepository.save(plantMaster);
    }

    @Transactional
    public void deletePlantMaster(String companyId, String plantId) {
        PlantMasterIdClass id = new PlantMasterIdClass(companyId, plantId);
        if (!plantMasterRepository.existsById(id)) {
            throw new RuntimeException("PlantMaster not found with id: " + id);
        }
        plantMasterRepository.deleteById(id);
    }
} 