package com.cmms4.workorder.repository;

import com.cmms4.workorder.entity.WorkOrderItem;
import com.cmms4.workorder.entity.WorkOrderItemIdClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Imports for WorkOrderItem and WorkOrderItemIdClass are not strictly needed here
// as they are in the same package.

@Repository
public interface WorkOrderItemRepository extends JpaRepository<WorkOrderItem, WorkOrderItemIdClass> {

    /**
     * Finds all WorkOrderItem entries for a given companyId and orderId,
     * ordered by itemId in ascending order.
     *
     * @param companyId The ID of the company.
     * @param orderId The ID of the work order.
     * @return A list of WorkOrderItem entities.
     */
    List<WorkOrderItem> findByCompanyIdAndOrderIdOrderByItemIdAsc(String companyId, String orderId);

    /**
     * Finds a specific WorkOrderItem by its full composite key.
     *
     * @param companyId The ID of the company.
     * @param orderId The ID of the work order.
     * @param itemId The ID of the item.
     * @return An Optional containing the WorkOrderItem if found, or empty otherwise.
     */
    Optional<WorkOrderItem> findByCompanyIdAndOrderIdAndItemId(String companyId, String orderId, String itemId);
}
