package com.cmms4.domain.roleAuth.service;

import com.cmms4.domain.roleAuth.entity.RoleAuth;
import com.cmms4.domain.roleAuth.entity.RoleAuthIdClass;
import com.cmms4.domain.roleAuth.repository.RoleAuthRepository;
// Assuming a way to validate pageId might involve a PageRepository or similar concept if pages are dynamic
// For now, we'll skip direct Page entity validation unless such an entity/repository is defined.
// import com.cmms4.domain.page.PageRepository; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * cmms4 - RoleService
 * 역할 권한 관리 서비스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Service
public class RoleAuthService {

    private final RoleAuthRepository roleAuthRepository;
    // private final PageRepository pageRepository; // Example: if Page entity/repository exists for validation

    public RoleAuthService(RoleAuthRepository roleAuthRepository /*, PageRepository pageRepository */) {
        this.roleAuthRepository = roleAuthRepository;
        // this.pageRepository = pageRepository;
    }

    @Transactional(readOnly = true)
    public List<RoleAuth> getRoleAuthsByRoleId(String roleId) {
        return roleAuthRepository.findByRoleId(roleId);
    }

    @Transactional(readOnly = true)
    public List<RoleAuth> getRoleAuthsByRoleAndPage(String roleId, String pageId) {
        return roleAuthRepository.findByRoleIdAndPageId(roleId, pageId);
    }

    /**
     * 역할 권한을 저장합니다.
     * 
     * @param roleAuth 저장할 역할 권한 정보
     * @return 저장된 역할 권한 정보
     * @throws IllegalArgumentException 필수 값이 누락된 경우
     */
    @Transactional
    public RoleAuth saveRoleAuth(RoleAuth roleAuth) {
        // 필수 값 검증
        if (roleAuth.getRoleId() == null || roleAuth.getRoleId().isEmpty() ||
            roleAuth.getPageId() == null || roleAuth.getPageId().isEmpty() ||
            roleAuth.getAuthGranted() == null || roleAuth.getAuthGranted().isEmpty()) {
            throw new IllegalArgumentException("역할ID, 페이지ID, 권한은 필수 입력값입니다.");
        }

        // 기존 권한이 있는지 확인
        RoleAuthIdClass id = new RoleAuthIdClass(
            roleAuth.getRoleId(), 
            roleAuth.getPageId(), 
            roleAuth.getAuthGranted()
        );
        
        Optional<RoleAuth> existingAuth = roleAuthRepository.findById(id);
        if (existingAuth.isPresent()) {
            // 기존 권한이 있으면 업데이트
            RoleAuth existing = existingAuth.get();
            existing.setAuthGranted(roleAuth.getAuthGranted());
            return roleAuthRepository.save(existing);
        }

        // 새로운 권한 저장
        return roleAuthRepository.save(roleAuth);
    }

    /**
     * 역할 권한을 삭제합니다.
     * 
     * @param roleAuthId 삭제할 역할 권한 ID
     * @throws EntityNotFoundException 해당 권한이 존재하지 않는 경우
     */
    @Transactional
    public void deleteRoleAuth(RoleAuthIdClass roleAuthId) {
        if (!roleAuthRepository.existsById(roleAuthId)) {
            throw new EntityNotFoundException("해당 권한이 존재하지 않습니다.");
        }
        roleAuthRepository.deleteById(roleAuthId);
    }

    @Transactional(readOnly = true)
    public Optional<RoleAuth> getRoleAuthById(RoleAuthIdClass roleAuthId) {
        return roleAuthRepository.findById(roleAuthId);
    }
}
