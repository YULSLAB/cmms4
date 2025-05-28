package com.cmms4.inventoryMaster.controller;

import com.cmms4.inventoryMaster.entity.InventoryMaster;
import com.cmms4.inventoryMaster.service.InventoryMasterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * cmms4 - InventoryMasterController
 * 재고 마스터 컨트롤러
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Controller
@RequestMapping("/inventoryMaster")
public class InventoryMasterController {

    private final InventoryMasterService inventoryMasterService;

    // Default companyId and siteId for demonstration if not provided in request
    private static final String DEFAULT_COMPANY_ID = "C0001";
    private static final String DEFAULT_SITE_ID = "S0001";

    public InventoryMasterController(InventoryMasterService inventoryMasterService) {
        this.inventoryMasterService = inventoryMasterService;
    }

    /**
     * 재고 마스터 목록 조회
     * @param companyId 회사 ID
     * @param siteId 사이트 ID
     * @param pageable 페이지 정보
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/list")
    public String listInventoryMasters(@RequestParam(defaultValue = DEFAULT_COMPANY_ID) String companyId,
                                     @RequestParam(defaultValue = DEFAULT_SITE_ID) String siteId,
                                     @PageableDefault(size = 10, sort = "inventoryId") Pageable pageable,
                                     Model model) {
        Page<InventoryMaster> inventoryPage = inventoryMasterService.getAllInventoryMasters(companyId, siteId, pageable);
        model.addAttribute("inventoryPage", inventoryPage);
        model.addAttribute("companyId", companyId);
        model.addAttribute("siteId", siteId);
        return "inventoryMaster/inventoryList";
    }

    /**
     * 새로운 재고 마스터 등록 폼
     * @param companyId 회사 ID
     * @param siteId 사이트 ID
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/new")
    public String showNewInventoryMasterForm(@RequestParam(defaultValue = DEFAULT_COMPANY_ID) String companyId,
                                           @RequestParam(defaultValue = DEFAULT_SITE_ID) String siteId,
                                           Model model) {
        InventoryMaster inventoryMaster = new InventoryMaster();
        inventoryMaster.setCompanyId(companyId);
        inventoryMaster.setSiteId(siteId);
        model.addAttribute("inventoryMaster", inventoryMaster);
        model.addAttribute("pageTitle", "Create New Inventory Master");
        return "inventoryMaster/inventoryForm";
    }

    /**
     * 재고 마스터 저장
     * @param inventoryMaster 재고 마스터 정보
     * @param redirectAttributes 리다이렉트 속성
     * @return 리다이렉트 URL
     */
    @PostMapping("/save")
    public String saveInventoryMaster(@ModelAttribute InventoryMaster inventoryMaster, 
                                    RedirectAttributes redirectAttributes) {
        if (inventoryMaster.getCompanyId() == null || inventoryMaster.getCompanyId().isEmpty()) {
            inventoryMaster.setCompanyId(DEFAULT_COMPANY_ID);
        }
        if (inventoryMaster.getSiteId() == null || inventoryMaster.getSiteId().isEmpty()) {
            inventoryMaster.setSiteId(DEFAULT_SITE_ID);
        }

        try {
            inventoryMasterService.saveInventoryMaster(inventoryMaster);
            redirectAttributes.addFlashAttribute("successMessage", "Inventory Master saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving Inventory Master: " + e.getMessage());
        }
        return "redirect:/inventoryMaster/inventoryMasterList?companyId=" + inventoryMaster.getCompanyId() + 
               "&siteId=" + inventoryMaster.getSiteId();
    }

    /**
     * 재고 마스터 수정 폼
     * @param inventoryId 재고 ID
     * @param companyId 회사 ID
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/edit/{inventoryId}")
    public String showEditInventoryMasterForm(@PathVariable String inventoryId,
                                            @RequestParam(defaultValue = DEFAULT_COMPANY_ID) String companyId,
                                            Model model) {
        Optional<InventoryMaster> inventoryMasterOpt = inventoryMasterService.getInventoryMasterById(companyId, inventoryId);
        if (inventoryMasterOpt.isPresent()) {
            model.addAttribute("inventoryMaster", inventoryMasterOpt.get());
            model.addAttribute("pageTitle", "Edit Inventory Master");
            return "inventoryMaster/inventoryForm";
        }
        return "redirect:/inventoryMaster/inventoryMasterList?companyId=" + companyId + "&siteId=" + DEFAULT_SITE_ID;
    }

    /**
     * 재고 마스터 삭제
     * @param inventoryId 재고 ID
     * @param companyId 회사 ID
     * @param siteId 사이트 ID
     * @param redirectAttributes 리다이렉트 속성
     * @return 리다이렉트 URL
     */
    @PostMapping("/delete/{inventoryId}")
    public String deleteInventoryMaster(@PathVariable String inventoryId,
                                      @RequestParam(defaultValue = DEFAULT_COMPANY_ID) String companyId,
                                      @RequestParam(defaultValue = DEFAULT_SITE_ID) String siteId,
                                      RedirectAttributes redirectAttributes) {
        try {
            inventoryMasterService.softDeleteInventoryMaster(companyId, inventoryId);
            redirectAttributes.addFlashAttribute("successMessage", "Inventory Master '" + inventoryId + "' marked as deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting Inventory Master: " + e.getMessage());
        }
        return "redirect:/inventoryMaster/inventoryMasterList?companyId=" + companyId + "&siteId=" + siteId;
    }
}
