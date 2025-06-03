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

    @GetMapping("/list")
    public String listWorkOrders(Model model,
                               HttpSession session,
                               @PageableDefault(size = 10, sort = "orderId") Pageable pageable) {
        String companyId = (String) session.getAttribute("companyId");
        String siteId = (String) session.getAttribute("siteId");
        
        Page<WorkOrder> workOrderPage = workOrderService.getAllWorkOrders(companyId, siteId, pageable);
        model.addAttribute("workOrderPage", workOrderPage);
        
        return "workOrder/workOrderList";
    }

    @GetMapping("/form")
    public String showWorkOrderForm(Model model) {
        model.addAttribute("workOrder", new WorkOrder());
        return "workOrder/workOrderForm";
    }

    @GetMapping("/detail/{orderId}")
    public String showWorkOrderDetail(@PathVariable Integer orderId,
                                    HttpSession session,
                                    Model model) {
        String companyId = (String) session.getAttribute("companyId");
        
        workOrderService.getWorkOrderByWorkOrderId(companyId, orderId)
            .ifPresent(workOrder -> {
                model.addAttribute("workOrder", workOrder);
                List<WorkOrderItem> items = workOrderService.getWorkOrderItems(companyId, orderId);
                model.addAttribute("workOrderItems", items);
            });
        
        return "workOrder/workOrderDetail";
    }

    @PostMapping("/save")
    public String saveWorkOrder(@ModelAttribute WorkOrder workOrder,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        try {
            workOrderService.saveWorkOrder(workOrder, username);
            redirectAttributes.addFlashAttribute("successMessage", "Work order saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save work order: " + e.getMessage());
        }
        return "redirect:/workOrder/list";
    }

    @PostMapping("/item/save")
    public String saveWorkOrderItem(@ModelAttribute WorkOrderItem workOrderItem,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        try {
            workOrderService.saveWorkOrderItem(workOrderItem, username);
            redirectAttributes.addFlashAttribute("successMessage", "Work order item saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save work order item: " + e.getMessage());
        }
        return "redirect:/workOrder/detail/" + workOrderItem.getOrderId();
    }

    @PostMapping("/delete/{orderId}")
    public String deleteWorkOrder(@PathVariable Integer orderId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        String companyId = (String) session.getAttribute("companyId");
        try {
            workOrderService.deleteWorkOrder(companyId, orderId);
            redirectAttributes.addFlashAttribute("successMessage", "Work order deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete work order: " + e.getMessage());
        }
        return "redirect:/workOrder/list";
    }

    @PostMapping("/item/delete/{orderId}/{itemId}")
    public String deleteWorkOrderItem(@PathVariable Integer orderId,
                                    @PathVariable Integer itemId,
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