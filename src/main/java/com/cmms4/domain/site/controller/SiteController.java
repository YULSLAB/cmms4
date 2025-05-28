package com.cmms4.domain.site.controller;

import com.cmms4.domain.site.entity.Site;
import com.cmms4.domain.site.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * cmms4 - SiteController
 * 사이트 관리 컨트롤러
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Controller
@RequestMapping("/site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    /**
     * 사이트 목록 화면
     */
    @GetMapping("/list")
    public String list(Model model, Authentication auth) {
        model.addAttribute("siteList", siteService.findByCompanyId(auth.getName()));
        return "site/siteList";
    }

    /**
     * 사이트 상세 화면
     */
    @GetMapping("/detail/{companyId}/{siteId}")
    public String detail(@PathVariable String companyId, 
                        @PathVariable String siteId, 
                        Model model) {
        model.addAttribute("site", siteService.findById(companyId, siteId));
        return "site/siteDetail";
    }

    /**
     * 사이트 등록/수정 화면
     */
    @GetMapping("/form")
    public String form(@RequestParam(required = false) String companyId,
                      @RequestParam(required = false) String siteId,
                      Model model) {
        if (companyId != null && siteId != null) {
            model.addAttribute("site", siteService.findById(companyId, siteId));
        }
        return "site/siteForm";
    }

    /**
     * 사이트 저장
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Site site) {
        siteService.save(site);
        return "redirect:/site/list";
    }

    /**
     * 사이트 삭제
     */
    @PostMapping("/delete/{companyId}/{siteId}")
    public String delete(@PathVariable String companyId,
                        @PathVariable String siteId) {
        siteService.delete(companyId, siteId);
        return "redirect:/site/list";
    }
} 