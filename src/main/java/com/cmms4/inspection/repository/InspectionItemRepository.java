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
    List<InspectionItem> findByCompanyIdAndInspectionIdAndScheduleIdOrderByItemIdAsc(String companyId, String inspectionId, String scheduleId);

    /**
     * Finds a specific InspectionItem by its full composite key.
     *
     * @param companyId The ID of the company.
     * @param inspectionId The ID of the inspection.
     * @param scheduleId The ID of the schedule.
     * @param itemId The ID of the item.
     * @return An Optional containing the InspectionItem if found, or empty otherwise.
     */
    Optional<InspectionItem> findByCompanyIdAndInspectionIdAndScheduleIdAndItemId(String companyId, String inspectionId, String scheduleId, String itemId);
}
