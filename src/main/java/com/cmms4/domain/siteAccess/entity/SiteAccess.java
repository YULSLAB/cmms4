package com.cmms4.domain.siteAccess.entity;

import com.cmms4.domain.site.entity.Site;
import com.cmms4.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

/**
 * cmms4 - SiteAccess
 * 사이트 접근 권한 관리 엔티티
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Getter
@Setter
@Entity
@Table(name = "siteAccess")
@IdClass(SiteAccessIdClass.class)
public class SiteAccess {

    @Id
    @Column(name = "companyId", length = 5, nullable = false)
    private String companyId;

    @Id
    @Column(name = "siteAccessId", length = 1, nullable = false)
    private String siteAccessId;

    @Id
    @Column(name = "username", length = 5, nullable = false)
    private String username;

    @Column(name = "siteId", length = 5, nullable = false)
    private String siteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "companyId", referencedColumnName = "companyId", insertable = false, updatable = false),
        @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    })
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "companyId", referencedColumnName = "companyId", insertable = false, updatable = false),
        @JoinColumn(name = "siteId", referencedColumnName = "siteId", insertable = false, updatable = false)
    })
    private Site site;

    // Constructors
    public SiteAccess() {
    }

    public SiteAccess(String companyId, String siteAccessId, String username, String siteId) {
        this.companyId = companyId;
        this.siteAccessId = siteAccessId;
        this.username = username;
        this.siteId = siteId;
    }

    // equals and hashCode (only for PK fields)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteAccess that = (SiteAccess) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(siteAccessId, that.siteAccessId) &&
               Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, siteAccessId, username);
    }
}
