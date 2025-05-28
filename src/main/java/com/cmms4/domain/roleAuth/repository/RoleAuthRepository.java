package com.cmms4.domain.roleAuth.repository;

import com.cmms4.domain.roleAuth.entity.RoleAuth;
import com.cmms4.domain.roleAuth.entity.RoleAuthIdClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleAuthRepository extends JpaRepository<RoleAuth, RoleAuthIdClass> {

    /**
     * Finds all role authorizations for a given roleId.
     * This method implicitly uses companyId if it were part of RoleAuth's PK
     * and part of the method name, e.g. findByCompanyIdAndRoleId.
     * Since companyId is not directly in RoleAuth, we assume roleId is unique enough or
     * company context is handled at a higher service level if RoleAuth entries are company-specific
     * but companyId is not part of RoleAuth table.
     * Based on the table structure, RoleAuth does not have companyId.
     *
     * @param roleId The ID of the role.
     * @return A list of RoleAuth entities.
     */
    List<RoleAuth> findByRoleId(String roleId);

    /**
     * Finds all role authorizations for a given roleId and pageId.
     * This helps retrieve all specific permissions (authGranted) for a role on a particular page.
     *
     * @param roleId The ID of the role.
     * @param pageId The ID of the page.
     * @return A list of RoleAuth entities.
     */
    List<RoleAuth> findByRoleIdAndPageId(String roleId, String pageId);
}
