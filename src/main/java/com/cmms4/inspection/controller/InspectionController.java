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

        // Initialize schedules and items for the form
        inspection.setSchedules(new ArrayList<>());
        inspection.setItems(new ArrayList<>());

        // Add one default empty schedule
        InspectionSchedule defaultSchedule = new InspectionSchedule();
        // defaultSchedule.setCompanyId(companyId); // Will be set during save
        // defaultSchedule.setScheduleId(0); // Temporary ID, will be managed by service/DB
        inspection.getSchedules().add(defaultSchedule);

        // Add one default empty item to the global list for the form
        inspection.getItems().add(new InspectionItem());

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
            Inspection inspection = inspectionOpt.get();

            // Populate the transient items list from the first schedule's items
            if (inspection.getSchedules() != null && !inspection.getSchedules().isEmpty()) {
                InspectionSchedule firstSchedule = inspection.getSchedules().get(0);
                if (firstSchedule.getItems() != null && !firstSchedule.getItems().isEmpty()) {
                    List<InspectionItem> templateItems = new ArrayList<>();
                    for (InspectionItem item : firstSchedule.getItems()) {
                        // Create new InspectionItem instances for the form to avoid modifying persisted entities directly
                        InspectionItem newItem = new InspectionItem();
                        newItem.setItemName(item.getItemName());
                        newItem.setItemLower(item.getItemLower());
                        newItem.setItemUpper(item.getItemUpper());
                        newItem.setItemStandard(item.getItemStandard());
                        newItem.setItemMethod(item.getItemMethod());
                        newItem.setItemUnit(item.getItemUnit());
                        newItem.setNotes(item.getNotes());
                        // Do not copy IDs (itemId, scheduleId, inspectionId, companyId) as these are template items
                        templateItems.add(newItem);
                    }
                    inspection.setItems(templateItems);
                } else {
                    inspection.setItems(new ArrayList<>()); // Initialize if no items in first schedule
                    inspection.getItems().add(new InspectionItem()); // Add a default empty item if none exist
                }
            } else {
                inspection.setItems(new ArrayList<>()); // Initialize if no schedules
                inspection.getItems().add(new InspectionItem()); // Add a default empty item
            }
            model.addAttribute("inspection", inspection);
            return "inspection/inspectionForm";
        }
        return "redirect:/inspection/inspectionList";
    }

    /** 저장 */
    @PostMapping("/inspectionSave")
    public String saveInspection(@ModelAttribute Inspection inspection, HttpSession session) {
        String username = (String) session.getAttribute("username"); // Get username for service layer
        // companyId and siteId are already part of the inspection object due to form binding.
        // Ensure they are set if not bound automatically.
        if (inspection.getCompanyId() == null) {
            inspection.setCompanyId((String) session.getAttribute("companyId"));
        }
         if (inspection.getSiteId() == null && session.getAttribute("siteId") != null) {
            inspection.setSiteId((String) session.getAttribute("siteId"));
        }

        if (inspection.getInspectionId() == null) {
            inspection.setCreateBy(username);
            inspection.setCreateDate(LocalDateTime.now());
        } else {
            // For existing inspections, make sure createBy and createDate are preserved if not part of the form
            Optional<Inspection> existingInspectionOpt = inspectionService.getInspectionByInspectionId(inspection.getCompanyId(), inspection.getInspectionId());
            if (existingInspectionOpt.isPresent()) {
                Inspection existingInspection = existingInspectionOpt.get();
                inspection.setCreateBy(existingInspection.getCreateBy());
                inspection.setCreateDate(existingInspection.getCreateDate());
            }
            inspection.setUpdateBy(username);
            inspection.setUpdateDate(LocalDateTime.now());
        }
        inspectionService.saveInspection(inspection, username); // Pass username
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
