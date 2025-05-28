package com.cmms4.workorder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.cmms4.workorder.entity.WorkOrder;
import com.cmms4.workorder.entity.WorkOrderIdClass;


// Imports for WorkOrder and WorkOrderIdClass are not strictly needed here
// as they are in the same package.

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, WorkOrderIdClass> {

    /**
     * Finds a page of WorkOrder entries for a given companyId and siteId.
     *
     * @param companyId The ID of the company.
     * @param siteId The ID of the site.
     * @param pageable Pagination information.
     * @return A page of WorkOrder entities.
     */
    Page<WorkOrder> findByCompanyIdAndSiteId(String companyId, String siteId, Pageable pageable);

    /**
     * Finds a specific WorkOrder by its companyId and orderId.
     *
     * @param companyId The ID of the company.
     * @param orderId The ID of the work order.
     * @return An Optional containing the WorkOrder if found, or empty otherwise.
     */
    Optional<WorkOrder> findByCompanyIdAndOrderId(String companyId, String orderId);

    /**
     * Finds all WorkOrder entries related to a specific plantId within a company.
     *
     * @param companyId The ID of the company.
     * @param plantId The ID of the plant.
     * @return A list of WorkOrder entities.
     */
    List<WorkOrder> findByCompanyIdAndPlantId(String companyId, String plantId);

    /**
     * Finds all WorkOrder entries related to a specific memoId within a company.
     * Note: The type of memoId in WorkOrder is currently String (CHAR(10)),
     * while in Memo entity it's Integer. This might require adjustment or careful handling.
     *
     * @param companyId The ID of the company.
     * @param memoId The ID of the memo.
     * @return A list of WorkOrder entities.
     */
    List<WorkOrder> findByCompanyIdAndMemoId(String companyId, String memoId);
}
