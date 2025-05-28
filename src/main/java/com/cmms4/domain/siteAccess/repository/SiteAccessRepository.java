package com.cmms4.domain.siteAccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmms4.domain.siteAccess.entity.SiteAccess;
import com.cmms4.domain.siteAccess.entity.SiteAccessIdClass;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteAccessRepository extends JpaRepository<SiteAccess, SiteAccessIdClass> {

    /**
     * Finds all site access entries for a given company and username.
     *
     * @param companyId The ID of the company.
     * @param username The username (corresponds to userId in the SiteAccess table).
     * @return A list of SiteAccess entities.
     */
    List<SiteAccess> findByCompanyIdAndUsername(String companyId, String username);

    /**
     * Finds all site access entries for a given company and siteId.
     * This can be used to find all users who have access to a particular site.
     *
     * @param companyId The ID of the company.
     * @param siteId The ID of the site.
     * @return A list of SiteAccess entities.
     */
    List<SiteAccess> findByCompanyIdAndSiteId(String companyId, String siteId);

    /**
     * Finds a specific SiteAccess entry by its full composite key.
     * JpaRepository's findById can also be used, but this provides a more explicit method name.
     *
     * @param companyId The ID of the company.
     * @param siteAccessId The site access ID (flag/type).
     * @param username The username (corresponds to userId in the SiteAccess table).
     * @return An Optional containing the SiteAccess entity if found, or empty otherwise.
     */
    Optional<SiteAccess> findByCompanyIdAndSiteAccessIdAndUsername(String companyId, String siteAccessId, String username);
}
