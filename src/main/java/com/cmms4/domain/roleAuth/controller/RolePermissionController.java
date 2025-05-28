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
 * cmms4 - RolePermissionController
 * 역할 권한 관리 컨트롤러
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Controller
@RequestMapping("/roleAuth/{roleId}/permissions")
public class RolePermissionController {

    private final RoleAuthService roleService;

    public RolePermissionController(RoleAuthService roleService) {
        this.roleService = roleService;
    }

    /**
     * 역할별 권한 목록을 조회합니다.
     * 
     * @param roleId 역할 ID
     * @param model 모델
     * @return 역할 권한 목록 화면
     */
    @GetMapping("/")
    public String listRolePermissions(@PathVariable String roleId, Model model) {
        model.addAttribute("roleId", roleId);
        List<RoleAuth> permissions = roleService.getRoleAuthsByRoleId(roleId);
        model.addAttribute("permissions", permissions);
        return "domain/role/rolePermissionList";
    }

    /**
     * 역할 권한 등록 화면을 조회합니다.
     * 
     * @param roleId 역할 ID
     * @param model 모델
     * @return 역할 권한 등록 화면
     */
    @GetMapping("/new")
    public String showNewRolePermissionForm(@PathVariable String roleId, Model model) {
        RoleAuth roleAuth = new RoleAuth();
        roleAuth.setRoleId(roleId);
        model.addAttribute("roleAuth", roleAuth);
        
        // 권한 유형 목록 설정
        List<String> authTypes = List.of("VIEW", "SAVE", "DELETE");
        model.addAttribute("authTypes", authTypes);

        return "domain/role/rolePermissionForm";
    }

    /**
     * 역할 권한을 저장합니다.
     * 
     * @param roleId 역할 ID
     * @param roleAuth 역할 권한 정보
     * @param redirectAttributes 리다이렉트 속성
     * @return 역할 권한 목록 화면으로 리다이렉트
     */
    @PostMapping("/save")
    public String saveRolePermission(@PathVariable String roleId,
                                     @ModelAttribute RoleAuth roleAuth,
                                     RedirectAttributes redirectAttributes) {
        roleAuth.setRoleId(roleId);

        if (roleAuth.getPageId() == null || roleAuth.getPageId().isEmpty() ||
            roleAuth.getAuthGranted() == null || roleAuth.getAuthGranted().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "페이지 ID와 권한 값은 필수 입력값입니다.");
            return "redirect:/roleAuth/" + roleId + "/permissions/new";
        }

        try {
            roleService.saveRoleAuth(roleAuth);
            redirectAttributes.addFlashAttribute("successMessage", "권한이 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/roleAuth/" + roleId + "/permissions";
    }

    /**
     * 역할 권한을 삭제합니다.
     * 
     * @param roleId 역할 ID
     * @param pageId 페이지 ID
     * @param redirectAttributes 리다이렉트 속성
     * @return 역할 권한 목록 화면으로 리다이렉트
     */
    @PostMapping("/delete")
    public String deleteRolePermission(@PathVariable String roleId,
                                       @RequestParam String pageId,
                                       RedirectAttributes redirectAttributes) {
        RoleAuthIdClass id = new RoleAuthIdClass(roleId, pageId, "DELETE");
        try {
            roleService.deleteRoleAuth(id);
            redirectAttributes.addFlashAttribute("successMessage", "권한이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/roleAuth/" + roleId + "/permissions";
    }
}
