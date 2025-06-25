package com.cmms4.domain.roleAuth.controller;

import com.cmms4.domain.roleAuth.entity.RoleAuth;
import com.cmms4.domain.roleAuth.entity.RoleAuthIdClass;
import com.cmms4.domain.roleAuth.service.RoleAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * cmms4 - RoleAuthController
 * 역할 권한 관리 컨트롤러
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Controller
@RequestMapping("/roleAuth/{roleId}")
public class RoleAuthController {

    private final RoleAuthService roleService;

    public RoleAuthController(RoleAuthService roleService) {
        this.roleService = roleService;
    }

    /** 역할별 권한 목록 조회 */
    @GetMapping("/")
    public String listRoleAuth(@PathVariable String roleId, Model model) {
        model.addAttribute("roleId", roleId);
        List<RoleAuth> authList = roleService.getRoleAuthByRoleId(roleId);
        model.addAttribute("authList", authList);
        return "domain/role/roleAuthList";
    }

    /** 역할 권한 등록/수정 화면 */
    @GetMapping("/edit")
    public String showEditRoleAuthForm(@PathVariable String roleId, @RequestParam(required = false) String authGranted, Model model) {
        RoleAuth roleAuth = null;
        if (authGranted != null) {
            // 수정: 복합키로 조회
            roleAuth = roleService.getRoleAuthByRoleIdAndAuthGranted(roleId, authGranted)
                    .orElse(null);
        }
        if (roleAuth == null) {
            roleAuth = new RoleAuth();
            roleAuth.setRoleId(roleId);
        }
        model.addAttribute("roleAuth", roleAuth);
        // 권한 유형 예시
        List<String> authTypes = List.of("ROLE_ADMIN", "ROLE_USER");
        model.addAttribute("authTypes", authTypes);
        return "domain/role/roleAuthForm";
    }

    /** 역할 권한 저장 */
    @PostMapping("/save")
    public String saveRoleAuth(@PathVariable String roleId,
                           @ModelAttribute RoleAuth roleAuth,
                           RedirectAttributes redirectAttributes) {
        roleAuth.setRoleId(roleId);

        if (roleAuth.getAuthGranted() == null || roleAuth.getAuthGranted().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한 값은 필수 입력값입니다.");
            return "redirect:/roleAuth/" + roleId + "/edit";
        }

        try {
            roleService.saveRoleAuth(roleAuth);
            redirectAttributes.addFlashAttribute("successMessage", "권한이 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/roleAuth/" + roleId + "/";
    }

    /** 역할 권한 삭제 */
    @PostMapping("/delete")
    public String deleteRoleAuth(@PathVariable String roleId,
                             @RequestParam String authGranted,
                             RedirectAttributes redirectAttributes) {
        try {
            roleService.deleteRoleAuth(roleId, authGranted);
            redirectAttributes.addFlashAttribute("successMessage", "권한이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/roleAuth/" + roleId + "/";
    }
}
