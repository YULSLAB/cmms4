package com.cmms4.workorder.service;

import com.cmms4.workorder.entity.WorkOrder;
import com.cmms4.workorder.entity.WorkOrderItem;
import com.cmms4.workorder.repository.WorkOrderRepository;
import com.cmms4.workorder.repository.WorkOrderItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class WorkOrderService {
    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderItemRepository workOrderItemRepository;

    public WorkOrderService(
            WorkOrderRepository workOrderRepository,
            WorkOrderItemRepository workOrderItemRepository) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderItemRepository = workOrderItemRepository;
    }

    @Transactional(readOnly = true)
    public Page<WorkOrder> getAllWorkOrders(String companyId, String siteId, Pageable pageable) {
        return workOrderRepository.findByCompanyIdAndSiteId(companyId, siteId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<WorkOrder> getWorkOrderByWorkOrderId(String companyId, Integer orderId) {
        return workOrderRepository.findByCompanyIdAndOrderId(companyId, orderId);
    }

    @Transactional(readOnly = true)
    public List<WorkOrderItem> getWorkOrderItems(String companyId, Integer orderId) {
        return workOrderItemRepository.findByCompanyIdAndOrderIdOrderByItemIdAsc(companyId, orderId);
    }

    @Transactional
public WorkOrder saveWorkOrder(WorkOrder workOrder, String username) {
    LocalDateTime now = LocalDateTime.now();
    
    if (workOrder.getOrderId() == null) {
        // findMaxOrderIdByCompanyId로 메소드명 수정
        Integer maxOrderId = workOrderRepository.findMaxOrderIdByCompanyId(workOrder.getCompanyId());
        int newOrderId = (maxOrderId == null) ? 500000000 : maxOrderId + 1;

        workOrder.setOrderId(newOrderId);
        workOrder.setCreateDate(now);
        workOrder.setCreateBy(username);
    }
    
    workOrder.setUpdateDate(now);
    workOrder.setUpdateBy(username);
    
    return workOrderRepository.save(workOrder);
}

    @Transactional
    public WorkOrderItem saveWorkOrderItem(WorkOrderItem workOrderItem, String username) {
 
        Integer maxItemId = workOrderItemRepository.findMaxItemIdByCompanyIdAndOrderId(
            workOrderItem.getCompanyId(), 
            workOrderItem.getOrderId()
        );
        int newItemId = (maxItemId == null) ? 1 : maxItemId + 1;
        workOrderItem.setItemId(newItemId);
        
        return workOrderItemRepository.save(workOrderItem);
    }

    @Transactional
    public void deleteWorkOrder(String companyId, Integer workOrderId) {
        Optional<WorkOrder> workOrderOpt = workOrderRepository.findByCompanyIdAndOrderId(
            companyId, 
            workOrderId
        );
        
        if (workOrderOpt.isPresent()) {
            WorkOrder workOrder = workOrderOpt.get();
            // Delete associated items first
            List<WorkOrderItem> items = workOrderItemRepository.findByCompanyIdAndOrderIdOrderByItemIdAsc(companyId, workOrderId);
            workOrderItemRepository.deleteAll(items);
            // Delete the work order
            workOrderRepository.delete(workOrder);
        } else {
            throw new RuntimeException("WorkOrder not found with ID: " + workOrderId);
        }
    }

    @Transactional
    public void deleteWorkOrderItem(String companyId, Integer workOrderId, Integer itemId) {
        Optional<WorkOrderItem> itemOpt = workOrderItemRepository.findByCompanyIdAndOrderIdAndItemId(
            companyId, 
            workOrderId, 
            itemId
        );
        
        if (itemOpt.isPresent()) {
            WorkOrderItem item = itemOpt.get();
            workOrderItemRepository.delete(item);
        } else {
            throw new RuntimeException("WorkOrderItem not found with ID: " + itemId);
        }
    }
}