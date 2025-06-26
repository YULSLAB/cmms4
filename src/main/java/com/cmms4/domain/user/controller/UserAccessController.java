package com.cmms4.domain.user.controller;

import com.cmms4.domain.siteAccess.entity.SiteAccess;
import com.cmms4.domain.siteAccess.service.SiteAccessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/siteAccess")
public class UserAccessController {
    private final SiteAccessService service;

    public UserAccessController(SiteAccessService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String list(@RequestParam String username,
                       @RequestParam String companyId, Model model) {
        List<SiteAccess> list = service.getAccessByUsername(companyId, username);
        model.addAttribute("accessList", list);
        model.addAttribute("username", username);
        return "domain/siteAccess/siteAccessList";
    }

    @GetMapping("/form")
    public String form(@RequestParam String username,
                       @RequestParam String companyId,
                       @RequestParam(required = false) String siteAccessId,
                       Model model) {
        SiteAccess access = siteAccessId != null ?
                service.get(companyId, siteAccessId, username).orElse(new SiteAccess()) : new SiteAccess();
        access.setCompanyId(companyId);
        access.setUsername(username);
        model.addAttribute("access", access);
        return "domain/siteAccess/siteAccessForm";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute SiteAccess access) {
        service.save(access);
        return "redirect:/siteAccess/list?username=" + access.getUsername() + "&companyId=" + access.getCompanyId();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String companyId,
                         @RequestParam String siteAccessId,
                         @RequestParam String username) {
        service.delete(companyId, siteAccessId, username);
        return "redirect:/siteAccess/list?username=" + username + "&companyId=" + companyId;
    }
}
