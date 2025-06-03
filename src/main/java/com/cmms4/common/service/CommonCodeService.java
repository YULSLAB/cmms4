package com.cmms4.common.service;

import com.cmms4.common.entity.CommonCode;
import com.cmms4.common.entity.CommonCodeItem;
import com.cmms4.common.repository.CommonCodeRepository;
import com.cmms4.common.repository.CommonCodeItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommonCodeService {
    private final CommonCodeRepository commonCodeRepository;
    private final CommonCodeItemRepository commonCodeItemRepository;

    public CommonCodeService(
            CommonCodeRepository commonCodeRepository,
            CommonCodeItemRepository commonCodeItemRepository) {
        this.commonCodeRepository = commonCodeRepository;
        this.commonCodeItemRepository = commonCodeItemRepository;
    }

    @Transactional(readOnly = true)
    public List<CommonCode> getAllCommonCodes(String companyId) {
        return commonCodeRepository.findByCompanyId(companyId);
    }

    @Transactional(readOnly = true)
    public List<CommonCode> getCommonCodesByType(String companyId, String codeType) {
        return commonCodeRepository.findByCompanyIdAndCodeType(companyId, codeType);
    }

    @Transactional(readOnly = true)
    public List<CommonCodeItem> getCommonCodeItems(String companyId, String codeId) {
        return commonCodeItemRepository.findByCompanyIdAndCodeIdOrderByCodeItemIdAsc(companyId, codeId);
    }

    @Transactional(readOnly = true)
    public Optional<CommonCodeItem> getCommonCodeItem(String companyId, String codeId, String codeItemId) {
        return commonCodeItemRepository.findByCompanyIdAndCodeIdAndCodeItemId(companyId, codeId, codeItemId);
    }

    @Transactional
    public CommonCode saveCommonCode(CommonCode commonCode, String username) {
        LocalDateTime now = LocalDateTime.now();
        commonCode.setCreateDate(now);
        commonCode.setCreateBy(username);
        commonCode.setUpdateDate(now);
        commonCode.setUpdateBy(username);
        
        return commonCodeRepository.save(commonCode);
    }

    @Transactional
    public CommonCodeItem saveCommonCodeItem(CommonCodeItem codeItem, String username) {
        LocalDateTime now = LocalDateTime.now();
        codeItem.setCreateDate(now);
        codeItem.setCreateBy(username);
        codeItem.setUpdateDate(now);
        codeItem.setUpdateBy(username);
        
        return commonCodeItemRepository.save(codeItem);
    }

    @Transactional
    public void deleteCommonCode(CommonCode commonCode) {
        commonCodeRepository.delete(commonCode);
    }

    @Transactional
    public void deleteCommonCodeItem(CommonCodeItem commonCodeItem) {
        commonCodeItemRepository.delete(commonCodeItem);
    }
}