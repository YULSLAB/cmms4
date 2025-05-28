package com.cmms4.plantMaster.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmms4.plantMaster.entity.PlantMaster;
import com.cmms4.plantMaster.entity.PlantMasterIdClass;

import java.util.List;
import java.util.Optional;

/**
 * cmms4 - PlantMasterRepository
 * 설비 마스터 정보 조회 인터페이스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Repository
public interface PlantMasterRepository extends JpaRepository<PlantMaster, PlantMasterIdClass> {

    /**
     * Finds all PlantMaster entries for a given companyId and siteId.
     *
     * @param companyId The ID of the company.
     * @param siteId The ID of the site.
     * @return A list of PlantMaster entities.
     */
    List<PlantMaster> findByCompanyIdAndSiteIdAndDeleteMarkIsNull(String companyId, String siteId);

    /**
     * Finds a page of PlantMaster entries for a given companyId and siteId.
     *
     * @param companyId The ID of the company.
     * @param siteId The ID of the site.
     * @param pageable Pagination information.
     * @return A page of PlantMaster entities.
     */
    Page<PlantMaster> findByCompanyIdAndSiteIdAndDeleteMarkIsNull(String companyId, String siteId, Pageable pageable);

    /**
     * Finds a specific PlantMaster by its companyId and plantId.
     * This is equivalent to JpaRepository's findById method when the IdClass is used.
     *
     * @param companyId The ID of the company.
     * @param plantId The ID of the plant.
     * @return An Optional containing the PlantMaster if found, or empty otherwise.
     */
    Optional<PlantMaster> findByCompanyIdAndPlantIdAndDeleteMarkIsNull(String companyId, String plantId);

}
