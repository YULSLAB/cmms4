package com.cmms4.auth.service;

import com.cmms4.domain.user.entity.User;
import com.cmms4.domain.user.repository.UserRepository;
import com.cmms4.domain.company.entity.Company;
import com.cmms4.domain.company.repository.CompanyRepository;
import com.cmms4.domain.site.entity.Site;
import com.cmms4.domain.site.repository.SiteRepository;
import com.cmms4.domain.dept.entity.Dept;
import com.cmms4.domain.dept.repository.DeptRepository;
import com.cmms4.auth.dto.CustomUserDetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * cmms4 - CustomUserDetailsService
 * Spring Security의 UserDetailsService 구현체
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final SiteRepository siteRepository;
    private final DeptRepository deptRepository;
    private static final String DEFAULT_COMPANY_ID = "C0001";

    public CustomUserDetailsService(
            UserRepository userRepository,
            CompanyRepository companyRepository,
            SiteRepository siteRepository,
            DeptRepository deptRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.siteRepository = siteRepository;
        this.deptRepository = deptRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== CustomUserDetailsService.loadUserByUsername ===");
        System.out.println("Attempting to load user with username: " + username);
        System.out.println("Using default company ID: " + DEFAULT_COMPANY_ID);

        // 사용자 정보 조회
        User user = userRepository.findByCompanyIdAndUsernameAndDeleteMarkIsNull(DEFAULT_COMPANY_ID, username)
                .orElseThrow(() -> {
                    System.out.println("User not found - CompanyId: " + DEFAULT_COMPANY_ID + ", Username: " + username);
                    return new UsernameNotFoundException("User not found with companyId: " + DEFAULT_COMPANY_ID + " and username: " + username);
                });
        System.out.println("User found: " + user.getUsername() + ", Full Name: " + user.getUserFullName());
        System.out.println("Stored password hash: " + user.getPassword());

        // 회사 정보 조회
        Company company = companyRepository.findByCompanyIdAndDeleteMarkIsNull(DEFAULT_COMPANY_ID)
                .orElseThrow(() -> {
                    System.out.println("Company not found - CompanyId: " + DEFAULT_COMPANY_ID);
                    return new UsernameNotFoundException("Company not found with id: " + DEFAULT_COMPANY_ID);
                });
        System.out.println("Company found: " + company.getCompanyName());

        // 사이트 정보 조회
        Site site = null;
        if (user.getSiteId() != null && !user.getSiteId().isEmpty()) {
            site = siteRepository.findByCompanyIdAndSiteIdAndDeleteMarkIsNull(DEFAULT_COMPANY_ID, user.getSiteId())
                    .orElse(null);
            System.out.println("Site found: " + (site != null ? site.getSiteName() : "null"));
        } else {
            System.out.println("No site ID provided for user");
        }

        // 부서 정보 조회
        Dept dept = null;
        if (user.getDeptId() != null && !user.getDeptId().isEmpty()) {
            dept = deptRepository.findByCompanyIdAndDeptIdAndDeleteMarkIsNull(DEFAULT_COMPANY_ID, user.getDeptId())
                    .orElse(null);
            System.out.println("Department found: " + (dept != null ? dept.getDeptName() : "null"));
        } else {
            System.out.println("No department ID provided for user");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        System.out.println("Granted authorities: " + authorities);

        CustomUserDetails userDetails = new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                authorities,
                user.getCompanyId(),
                company.getCompanyName(),
                user.getSiteId(),
                site != null ? site.getSiteName() : "",
                user.getDeptId(),
                dept != null ? dept.getDeptName() : "",
                user.getUserFullName()
        );
        System.out.println("Created CustomUserDetails for: " + userDetails.getUsername());
        System.out.println("===============================================");
        return userDetails;
    }
}
