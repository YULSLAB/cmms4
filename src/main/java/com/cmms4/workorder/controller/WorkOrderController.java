package com.cmms4.workorder.controller;

import com.cmms4.workorder.entity.WorkOrder;
import com.cmms4.workorder.entity.WorkOrderItem;
import com.cmms4.workorder.service.WorkOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/workOrder")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @GetMapping("/workOrderList")
    public String list(Model model,
                               HttpSession session,
                               @PageableDefault(size = 10, sort = "orderId") Pageable pageable) {
        
        // 세션에서 사용자 정보 가져오기
        String companyId = (String) session.getAttribute("companyId");
        String siteId = (String) session.getAttribute("siteId");
        
        Page<WorkOrder> workOrderPage = workOrderService.getAllWorkOrders(companyId, siteId, pageable);
        model.addAttribute("workOrderPage", workOrderPage);
        
        return "workOrder/workOrderList";
    }

    @GetMapping("/workOrderForm")
    public String form(Model model, HttpSession session) {
        String companyId = (String) session.getAttribute("companyId");
        String siteId = (String) session.getAttribute("siteId");

        WorkOrder workOrder = new WorkOrder();
        workOrder.setCompanyId(companyId);
        workOrder.setSiteId(siteId);

        // 최소 1개 항목 생성 (초기화용)
        WorkOrderItem item = new WorkOrderItem();
        item.setCompanyId(companyId);
        workOrder.getItems().add(item);

        model.addAttribute("workOrder", workOrder);
        return "workOrder/workOrderForm";
    }


    @GetMapping("/workOrderDetail/{orderId}")
    public String detail(@PathVariable String orderId,
                                    HttpSession session,
                                    Model model) {
        // 세션에서 사용자 정보 가져오기
        String companyId = (String) session.getAttribute("companyId");
        
        workOrderService.getWorkOrderByWorkOrderId(companyId, orderId)
            .ifPresent(workOrder -> {
                model.addAttribute("workOrder", workOrder);
                List<WorkOrderItem> items = workOrderService.getWorkOrderItems(companyId, orderId);
                model.addAttribute("workOrderItems", items);
            });
        
        return "workOrder/workOrderDetail";
    }

    @PostMapping("/workOrderSave")
    public String save(@ModelAttribute WorkOrder workOrder,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        // 세션에서 사용자 정보 가져오기
        String username = (String) session.getAttribute("username");

        try {
            workOrderService.saveWorkOrder(workOrder, username);
            redirectAttributes.addFlashAttribute("successMessage", "Work order saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save work order: " + e.getMessage());
        }
        return "redirect:/workOrder/workOrderList";  // Fix redirect path
    }

    @PostMapping("/item/workOrderItemSave")
    public String saveItem(@ModelAttribute WorkOrderItem workOrderItem,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        
        // 세션에서 사용자 정보 가져오기
        String username = (String) session.getAttribute("username");
        try {
            workOrderService.saveWorkOrderItem(workOrderItem, username);
            redirectAttributes.addFlashAttribute("successMessage", "Work order item saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save work order item: " + e.getMessage());
        }
        return "redirect:/workOrder/detail/" + workOrderItem.getOrderId();
    }

    @PostMapping("/workOrderDelete/{orderId}")
    public String delete(@PathVariable String orderId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        
        // 세션에서 사용자 정보 가져오기
        String companyId = (String) session.getAttribute("companyId");
        try {
            workOrderService.deleteWorkOrder(companyId, orderId);
            redirectAttributes.addFlashAttribute("successMessage", "Work order deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete work order: " + e.getMessage());
        }
        return "redirect:/workOrder/workOrderList";  // Fix redirect path
    }

    @PostMapping("/item/workOrderItemDelete/{orderId}/{itemId}")
    public String deleteItem(@PathVariable String orderId,
                                    @PathVariable String itemId,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        
        String companyId = (String) session.getAttribute("companyId");
        try {
            workOrderService.deleteWorkOrderItem(companyId, orderId, itemId);
            redirectAttributes.addFlashAttribute("successMessage", "Work order item deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete work order item: " + e.getMessage());
        }
        return "redirect:/workOrder/detail/" + orderId;
    }
}