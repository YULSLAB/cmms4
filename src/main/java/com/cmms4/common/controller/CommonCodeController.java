package com.cmms4.common.controller;

import com.cmms4.common.entity.CommonCode;
import com.cmms4.common.entity.CommonCodeItem;
import com.cmms4.common.service.CommonCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/commonCode")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    public CommonCodeController(CommonCodeService commonCodeService) {
        this.commonCodeService = commonCodeService;
    }

    @GetMapping("/list")
    public String listCommonCodes(Model model, HttpSession session) {
        String companyId = (String) session.getAttribute("companyId");
        List<CommonCode> codes = commonCodeService.getAllCommonCodes(companyId);
        model.addAttribute("codes", codes);
        return "commonCode/commonCodeList";
    }

    @GetMapping("/list/{codeType}")
    public String listCommonCodesByType(
            @PathVariable String codeType,
            Model model,
            HttpSession session) {
        String companyId = (String) session.getAttribute("companyId");
        List<CommonCode> codes = commonCodeService.getCommonCodesByCodeType(companyId, codeType);
        model.addAttribute("codes", codes);
        model.addAttribute("codeType", codeType);
        return "commonCode/commonCodeList";
    }

    @GetMapping("/items/{codeId}")
    public String listCommonCodeItems(
            @PathVariable String codeId,
            Model model,
            HttpSession session) {
        String companyId = (String) session.getAttribute("companyId");
        List<CommonCodeItem> items = commonCodeService.getCommonCodeItems(companyId, codeId);
        model.addAttribute("items", items);
        model.addAttribute("codeId", codeId);
        return "commonCode/commonCodeItemList";
    }

    @GetMapping("/form")
    public String showCommonCodeForm(Model model) {
        // 새로운 공통 코드 등록 폼을 위한 모델 초기화
        model.addAttribute("commonCode", new CommonCode());
        // 공통 코드 항목 리스트 초기화
        model.addAttribute("codeItem", new CommonCodeItem());
 
        return "commonCode/commonCodeForm";
    }

    @GetMapping("/item/form/{codeId}")
    public String showCommonCodeItemForm(
            @PathVariable String codeId,
            Model model) {
        CommonCodeItem item = new CommonCodeItem();
        item.setCodeId(codeId);
        model.addAttribute("codeItem", item);
        return "commonCode/commonCodeItemForm";
    }

    @PostMapping("/save")
    public String saveCommonCode(
            @ModelAttribute CommonCode commonCode,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        try {
            commonCodeService.saveCommonCode(commonCode, username);
            redirectAttributes.addFlashAttribute("successMessage", "Common code saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save common code: " + e.getMessage());
        }
        return "redirect:/commonCode/list";
    }

    @PostMapping("/item/save")
    public String saveCommonCodeItem(
            @ModelAttribute CommonCodeItem codeItem,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        try {
            commonCodeService.saveCommonCodeItem(codeItem, username);
            redirectAttributes.addFlashAttribute("successMessage", "Common code item saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save common code item: " + e.getMessage());
        }
        return "redirect:/commonCode/items/" + codeItem.getCodeId();
    }

    @PostMapping("/delete")
    public String deleteCommonCode(
            @ModelAttribute CommonCode commonCode,
            RedirectAttributes redirectAttributes) {
        try {
            commonCodeService.deleteCommonCode(commonCode);
            redirectAttributes.addFlashAttribute("successMessage", "Common code deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete common code: " + e.getMessage());
        }
        return "redirect:/commonCode/list";
    }

    @PostMapping("/item/delete")
    public String deleteCommonCodeItem(
            @ModelAttribute CommonCodeItem codeItem,
            RedirectAttributes redirectAttributes) {
        String codeId = codeItem.getCodeId();
        try {
            commonCodeService.deleteCommonCodeItem(codeItem);
            redirectAttributes.addFlashAttribute("successMessage", "Common code item deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete common code item: " + e.getMessage());
        }
        return "redirect:/commonCode/items/" + codeId;
    }
}