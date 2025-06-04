package com.cmms4.inspection.repository; // << UPDATED PACKAGE

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmms4.inspection.entity.InspectionItem;
import com.cmms4.inspection.entity.InspectionItemIdClass;

import java.util.List;
import java.util.Optional;

// Imports for InspectionItem and InspectionItemIdClass are not strictly needed here
// as they are in the same package.

@Repository
public interface InspectionItemRepository extends JpaRepository<InspectionItem, InspectionItemIdClass> {

    /**
     * Finds all InspectionItem entries for a given companyId, inspectionId, and scheduleId,
     * ordered by itemId in ascending order.
     *
     * @param companyId The ID of the company.
     * @param inspectionId The ID of the inspection.
     * @param scheduleId The ID of the schedule.
     * @return A list of InspectionItem entities.
     */
    List<InspectionItem> findByCompanyIdAndInspectionIdAndScheduleIdOrderByItemIdAsc(String companyId, Integer inspectionId, Integer scheduleId);

    /**
     * Finds a specific InspectionItem by its full composite key.
     *
     * @param companyId The ID of the company.
     * @param inspectionId The ID of the inspection.
     * @param scheduleId The ID of the schedule.
     * @param itemId The ID of the item.
     * @return An Optional containing the InspectionItem if found, or empty otherwise.
     */
    Optional<InspectionItem> findByCompanyIdAndInspectionIdAndScheduleIdAndItemId(String companyId, Integer inspectionId, Integer scheduleId, Integer itemId);
    
    /**
     * Deletes all InspectionItem entries for a given companyId and inspectionId.
     *
     * @param companyId The ID of the company.
     * @param inspectionId The ID of the inspection.
     */
    void deleteByCompanyIdAndInspectionId(String companyId, Integer inspectionId);

    /**
     * companyId, inspectionId, scheduleId 로 삭제
     * @param companyId 회사 ID
     * @param inspectionId 점검 ID
     * @param scheduleId 일정 ID
     */
    void deleteByCompanyIdAndInspectionIdAndScheduleId(String companyId, Integer inspectionId, Integer scheduleId);

    /**
     * companyId, inspectionId, scheduleId, itemId 로 삭제
     * @param companyId 회사 ID 
     * @param inspectionId 점검 ID
     * @param scheduleId 일정 ID
     * @param itemId 항목 ID
     */
    void deleteByCompanyIdAndInspectionIdAndScheduleIdAndItemId(String companyId, Integer inspectionId, Integer scheduleId, Integer itemId);
 }
