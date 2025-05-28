package com.cmms4.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmms4.common.entity.CommonCode;
import com.cmms4.common.entity.CommonCodeIdClass;

import java.util.List;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeIdClass> {

    /**
     * Finds all CommonCode entries for a given companyId.
     *
     * @param companyId The ID of the company.
     * @return A list of CommonCode entities.
     */
    List<CommonCode> findByCompanyId(String companyId);

    /**
     * Finds CommonCode entries by companyId and codeType.
     * This can be useful for fetching all codes of a specific type, e.g., all "ASSET" types.
     *
     * @param companyId The ID of the company.
     * @param codeType The type of the code (e.g., "ASSET", "JOBYP").
     * @return A list of CommonCode entities.
     */
    List<CommonCode> findByCompanyIdAndCodeType(String companyId, String codeType);
}
