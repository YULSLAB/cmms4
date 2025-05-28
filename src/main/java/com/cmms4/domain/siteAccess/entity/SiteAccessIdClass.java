package com.cmms4.domain.siteAccess.entity;

import java.io.Serializable;
import java.util.Objects;

public class SiteAccessIdClass implements Serializable {
    private String companyId;
    private String siteAccessId;
    private String username; // Corresponds to userId in the table, aliased to username

    // Constructors
    public SiteAccessIdClass() {
    }

    public SiteAccessIdClass(String companyId, String siteAccessId, String username) {
        this.companyId = companyId;
        this.siteAccessId = siteAccessId;
        this.username = username;
    }

    // Getters and Setters
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSiteAccessId() {
        return siteAccessId;
    }

    public void setSiteAccessId(String siteAccessId) {
        this.siteAccessId = siteAccessId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteAccessIdClass that = (SiteAccessIdClass) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(siteAccessId, that.siteAccessId) &&
               Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, siteAccessId, username);
    }
}
