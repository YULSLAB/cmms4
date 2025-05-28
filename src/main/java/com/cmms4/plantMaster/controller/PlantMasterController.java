package com.cmms4.plantMaster.controller;

import com.cmms4.plantMaster.entity.PlantMaster;
import com.cmms4.plantMaster.service.PlantMasterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * cmms4 - PlantMasterController
 * 설비 관리 컨트롤러
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Controller
@RequestMapping("/plantMaster")
public class PlantMasterController {

    private final PlantMasterService plantMasterService;

    // 기본 companyId와 siteId 값
    private static final String DEFAULT_COMPANY_ID = "C0001";
    private static final String DEFAULT_SITE_ID = "S0001";

    public PlantMasterController(PlantMasterService plantMasterService) {
        this.plantMasterService = plantMasterService;
    }

    @GetMapping("/list")
    public String listPlantMasters(@RequestParam(defaultValue = DEFAULT_COMPANY_ID) String companyId,
                                 @RequestParam(defaultValue = DEFAULT_SITE_ID) String siteId,
                                 @PageableDefault(size = 10, sort = "plantId") Pageable pageable,
                                 Model model) {
        Page<PlantMaster> plantPage = plantMasterService.getAllPlantMasters(companyId, siteId, pageable);
        model.addAttribute("plantPage", plantPage);
        model.addAttribute("companyId", companyId);
        model.addAttribute("siteId", siteId);
        return "plantMaster/plantMasterList";
    }

    @GetMapping("/new")
    public String showNewPlantMasterForm(@RequestParam(defaultValue = DEFAULT_COMPANY_ID) String companyId,
                                       @RequestParam(defaultValue = DEFAULT_SITE_ID) String siteId,
                                       Model model) {
        PlantMaster plantMaster = new PlantMaster();
        plantMaster.setCompanyId(companyId);
        plantMaster.setSiteId(siteId);
        model.addAttribute("plantMaster", plantMaster);
        model.addAttribute("pageTitle", "Create New Plant Master");
        return "plantMaster/plantMasterForm";
    }

    @GetMapping("/edit/{plantId}")
    public String showEditPlantMasterForm(@PathVariable String plantId,
                                        @RequestParam(defaultValue = DEFAULT_COMPANY_ID) String companyId,
                                        Model model) {
        Optional<PlantMaster> plantMasterOpt = plantMasterService.getPlantMasterById(companyId, plantId);
        if (plantMasterOpt.isPresent()) {
            model.addAttribute("plantMaster", plantMasterOpt.get());
            model.addAttribute("pageTitle", "Edit Plant Master");
            return "plantMaster/plantMasterForm";
        }
        return "redirect:/plantMaster/plantMasterList?companyId=" + companyId + "&siteId=" + DEFAULT_SITE_ID;
    }

    @PostMapping("/save")
    public String savePlantMaster(@ModelAttribute PlantMaster plantMaster, 
                                RedirectAttributes redirectAttributes) {
        try {
            if (plantMaster.getCompanyId() == null || plantMaster.getCompanyId().isEmpty()) {
                plantMaster.setCompanyId(DEFAULT_COMPANY_ID);
            }
            if (plantMaster.getSiteId() == null || plantMaster.getSiteId().isEmpty()) {
                plantMaster.setSiteId(DEFAULT_SITE_ID);
            }

            plantMasterService.savePlantMaster(plantMaster);
            redirectAttributes.addFlashAttribute("successMessage", "Plant Master saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving Plant Master: " + e.getMessage());
        }
        return "redirect:/plantMaster/plantMasterList?companyId=" + plantMaster.getCompanyId() + 
               "&siteId=" + plantMaster.getSiteId();
    }

    @PostMapping("/delete/{plantId}")
    public String deletePlantMaster(@PathVariable String plantId,
                                  @RequestParam(defaultValue = DEFAULT_COMPANY_ID) String companyId,
                                  @RequestParam(defaultValue = DEFAULT_SITE_ID) String siteId,
                                  RedirectAttributes redirectAttributes) {
        try {
            plantMasterService.deletePlantMaster(companyId, plantId);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Plant Master '" + plantId + "' deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error deleting Plant Master: " + e.getMessage());
        }
        return "redirect:/plantMaster/plantMasterList?companyId=" + companyId + "&siteId=" + siteId;
    }
}
