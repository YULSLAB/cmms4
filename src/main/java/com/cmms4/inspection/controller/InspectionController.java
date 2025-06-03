package com.cmms4.inspection.controller;

import com.cmms4.inspection.entity.Inspection;
import com.cmms4.inspection.service.InspectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import java.util.Optional;

/**
 * cmms4 - InspectionController
 * 점검 관리 컨트롤러
 * 
 * @author cmms4
 * @since 2025-05-29
 */
@Controller
@RequestMapping("/inspection")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    /**
     * 점검 목록 조회
     * @param model 모델
     * @param session 세션
     * @param pageable 페이지 정보
     * @return 뷰 이름
     */
    @GetMapping("/inspectionList")
    public String list(Model model,
                                HttpSession session,
                                @PageableDefault(size = 10, sort = "inspectionId") Pageable pageable) {
        // 세션에서 사용자 정보 가져오기
        String companyId = (String) session.getAttribute("companyId");
        String siteId = (String) session.getAttribute("siteId");
        
        Page<Inspection> inspectionPage = inspectionService.getAllInspections(companyId, siteId, pageable);
        model.addAttribute("inspectionPage", inspectionPage);
        model.addAttribute("companyId", companyId);
        model.addAttribute("siteId", siteId);
        return "inspection/inspectionList";
    }

    /**
     * 새로운 점검 등록 폼
     * @param model 모델
     * @param session 세션
     * @return 뷰 이름
     */
    @GetMapping("/inspectionForm")
    public String form(Model model, HttpSession session) {
        return "inspection/inspectionForm";
    }

    /**
     * 점검 수정 폼
     * @param inspectionId 점검 ID
     * @param session 세션
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/inspectionDetail/{inspectionId}")
    public String detail(@PathVariable Integer inspectionId,
                                       HttpSession session,
                                       Model model) {
        // 세션에서 사용자 정보 가져오기
        String companyId = (String) session.getAttribute("companyId");

        Optional<Inspection> inspectionOpt = inspectionService.getInspectionByInspectionId(companyId, inspectionId);
        if (inspectionOpt.isPresent()) {
            model.addAttribute("inspection", inspectionOpt.get());
            model.addAttribute("pageTitle", "점검 수정");
            return "inspection/inspectionForm";
        }
        return "redirect:/inspection/list";
    }

    /**
     * 점검 저장
     * @param inspection 점검 정보
     * @param session 세션
     * @param redirectAttributes 리다이렉트 속성
     * @return 리다이렉트 URL
     */
    @PostMapping("/save")
    public String saveInspection(@ModelAttribute Inspection inspection,
                               HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        String companyId = (String) session.getAttribute("companyId");
        String siteId = (String) session.getAttribute("siteId");
        inspection.setCompanyId(companyId);
        inspection.setSiteId(siteId);

        if (inspection.getInspectionId() == null) {
            inspection.setCreateBy((String) session.getAttribute("username"));
            inspection.setCreateDate(LocalDateTime.now());
        } else {
            inspection.setUpdateBy((String) session.getAttribute("username"));
            inspection.setUpdateDate(LocalDateTime.now());
        }

        inspectionService.saveInspection(inspection, inspection.getCreateBy());
        return "redirect:/inspection/list";
    }

    /**
     * 점검 삭제
     * @param inspectionId 점검 ID
     * @param session 세션
     * @return 리다이렉트 URL
     */
    @PostMapping("/delete/{inspectionId}")
    public String deleteInspection(@PathVariable String inspectionId,
                                 HttpSession session) {
        try {
            // 세션에서 사용자 정보 가져오기
            String companyId = (String) session.getAttribute("companyId");
            
            inspectionService.deleteInspection(companyId, inspectionId);
        } catch (Exception e) {
            throw new RuntimeException("점검 삭제 중 오류 발생: " + e.getMessage());
        }
        return "redirect:/inspection/list";
    }
}