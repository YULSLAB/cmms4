package com.cmms4.domain.siteAccess.service;

import com.cmms4.domain.siteAccess.entity.SiteAccess;
import com.cmms4.domain.siteAccess.entity.SiteAccessIdClass;
import com.cmms4.domain.siteAccess.repository.SiteAccessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SiteAccessService {
    private final SiteAccessRepository repository;

    public SiteAccessService(SiteAccessRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<SiteAccess> getAccessByUsername(String companyId, String username) {
        return repository.findByCompanyIdAndUsername(companyId, username);
    }

    public SiteAccess save(SiteAccess access) {
        return repository.save(access);
    }

    public void delete(String companyId, String siteAccessId, String username) {
        repository.deleteById(new SiteAccessIdClass(companyId, siteAccessId, username));
    }

    @Transactional(readOnly = true)
    public Optional<SiteAccess> get(String companyId, String siteAccessId, String username) {
        return repository.findByCompanyIdAndSiteAccessIdAndUsername(companyId, siteAccessId, username);
    }
}
