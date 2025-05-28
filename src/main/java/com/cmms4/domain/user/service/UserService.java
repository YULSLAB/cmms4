package com.cmms4.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import com.cmms4.domain.user.repository.UserRepository;
import com.cmms4.domain.user.entity.User;

/**
 * cmms4 - UserService
 * 사용자 관리 서비스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자 조회 (로그인용)
     * @param companyId 회사 ID
     * @param username 사용자 ID
     * @return 사용자 정보 (삭제되지 않은 사용자만)
     */
    @Transactional(readOnly = true)
    public Optional<User> getUser(String companyId, String username) {
        return userRepository.findByCompanyIdAndUsernameAndDeleteMarkIsNull(companyId, username);
    }
} 