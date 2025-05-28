package com.cmms4.domain.roleAuth.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * cmms4 - RoleAuthIdClass
 * 역할 권한 복합키 클래스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
public class RoleAuthIdClass implements Serializable {
    private String roleId;
    private String pageId;
    private String authGranted;

    // Constructors
    public RoleAuthIdClass() {
    }

    public RoleAuthIdClass(String roleId, String pageId, String authGranted) {
        this.roleId = roleId;
        this.pageId = pageId;
        this.authGranted = authGranted;
    }

    // Getters and Setters
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getAuthGranted() {
        return authGranted;
    }

    public void setAuthGranted(String authGranted) {
        this.authGranted = authGranted;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleAuthIdClass that = (RoleAuthIdClass) o;
        return Objects.equals(roleId, that.roleId) &&
               Objects.equals(pageId, that.pageId) &&
               Objects.equals(authGranted, that.authGranted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, pageId, authGranted);
    }
}
