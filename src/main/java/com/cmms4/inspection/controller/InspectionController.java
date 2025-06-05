package com.cmms4.inspection.controller;

import com.cmms4.inspection.entity.Inspection;
import com.cmms4.inspection.entity.InspectionItem;
import com.cmms4.inspection.entity.InspectionSchedule;
import com.cmms4.inspection.service.InspectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * cmms4 - InspectionController
 * 점검 관리 컨트롤러
 */
@Controller
@RequestMapping("/inspection")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    /** 목록 조회 */
    @GetMapping("/inspectionList")
    public String list(Model model,
                       HttpSession session,
                       @PageableDefault(size = 10, sort = "inspectionId") Pageable pageable) {
        String companyId = (String) session.getAttribute("companyId");
        String siteId = (String) session.getAttribute("siteId");
        Page<Inspection> inspectionPage = inspectionService.getAllInspections(companyId, siteId, pageable);
        model.addAttribute("inspectionPage", inspectionPage);
        model.addAttribute("companyId", companyId);
        model.addAttribute("siteId", siteId);
        return "inspection/inspectionList";
    }

    /** 신규 폼 */
    @GetMapping("/inspectionForm")
    public String form(Model model, HttpSession session) {
        String companyId = (String) session.getAttribute("companyId");
        String siteId = (String) session.getAttribute("siteId");
        Inspection inspection = new Inspection();
        inspection.setCompanyId(companyId);
        inspection.setSiteId(siteId);
        InspectionSchedule schedule = new InspectionSchedule();
        schedule.setItems(new ArrayList<>());
        schedule.getItems().add(new InspectionItem());
        inspection.setSchedules(new ArrayList<>());
        inspection.getSchedules().add(schedule);
        model.addAttribute("inspection", inspection);
        model.addAttribute("companyId", companyId);
        model.addAttribute("siteId", siteId);
        return "inspection/inspectionForm";
    }

    /** 수정 폼 */
    @GetMapping("/inspectionDetail/{inspectionId}")
    public String detail(@PathVariable Integer inspectionId,
                         HttpSession session,
                         Model model) {
        String companyId = (String) session.getAttribute("companyId");
        Optional<Inspection> inspectionOpt = inspectionService.getInspectionByInspectionId(companyId, inspectionId);
        if (inspectionOpt.isPresent()) {
            model.addAttribute("inspection", inspectionOpt.get());
            return "inspection/inspectionForm";
        }
        return "redirect:/inspection/inspectionList";
    }

    /** 저장 */
    @PostMapping("/inspectionSave")
    public String saveInspection(@ModelAttribute Inspection inspection, HttpSession session) {
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
        return "redirect:/inspection/inspectionList";
    }

    /** 삭제 */
    @PostMapping("/inspectionDelete/{inspectionId}")
    public String deleteInspection(@PathVariable Integer inspectionId, HttpSession session) {
        try {
            String companyId = (String) session.getAttribute("companyId");
            inspectionService.deleteInspection(companyId, inspectionId);
        } catch (Exception e) {
            throw new RuntimeException("점검 삭제 중 오류 발생: " + e.getMessage());
        }
        return "redirect:/inspection/inspectionList";
    }

    /** 결과 입력 폼 */
    @GetMapping("/resultForm/{inspectionId}")
    public String resultForm(@PathVariable Integer inspectionId, HttpSession session, Model model) {
        String companyId = (String) session.getAttribute("companyId");
        Optional<Inspection> opt = inspectionService.getInspectionByInspectionId(companyId, inspectionId);
        if (opt.isPresent()) {
            model.addAttribute("inspection", opt.get());
            return "inspection/inspectionResultForm";
        }
        return "redirect:/inspection/inspectionList";
    }

    /** 결과 저장 */
    @PostMapping("/resultSave")
    public String saveResult(@ModelAttribute Inspection inspection, HttpSession session) {
        inspectionService.saveInspectionResults(inspection);
        return "redirect:/inspection/inspectionList";
    }
}
