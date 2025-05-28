package com.cmms4.memo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cmms4.memo.entity.Memo;
import com.cmms4.memo.entity.MemoIdClass;

import java.util.List;
import java.util.Optional;

// Imports for Memo and MemoIdClass are not strictly needed here
// as they are in the same package.

/**
 * cmms4 - MemoRepository
 * 메모 레포지토리
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Repository
public interface MemoRepository extends JpaRepository<Memo, MemoIdClass> {
    // Example of a custom query method (optional for now)
    // List<Memo> findByCompanyIdAndSiteIdAndDelMarkYNEOrderByMemoNameAsc(String companyId, String siteId, String delMarkYN);

    /**
     * 특정 메모를 조회합니다. (삭제되지 않은 메모만)
     *
     * @param companyId 회사 ID
     * @param memoId 메모 ID
     * @param deleteMark 삭제 여부 (삭제되지 않은 메모만 조회)
     * @return Optional<Memo> 메모 정보
     */
    Optional<Memo> findByCompanyIdAndMemoIdAndDeleteMarkIsNull(String companyId, Integer memoId);

    /**
     * 회사 ID, 사이트 ID로 삭제되지 않은 메모 목록을 페이지네이션하여 조회합니다.
     *
     * @param companyId 회사 ID
     * @param siteId 사이트 ID
     * @param pageable 페이지 정보
     * @return Page<Memo> 메모 페이지
     */
    Page<Memo> findByCompanyIdAndSiteIdAndDeleteMarkIsNull(String companyId, String siteId, Pageable pageable);

    /**
     * 회사 ID, 사이트 ID로 삭제되지 않은 메모 목록을 조회합니다.
     *
     * @param companyId 회사 ID
     * @param siteId 사이트 ID
     * @return List<Memo> 메모 목록
     */
    List<Memo> findByCompanyIdAndSiteIdAndDeleteMarkIsNull(String companyId, String siteId);

    /**
     * 회사 ID, 사이트 ID로 삭제되지 않은 메모 목록을 메모 이름으로 정렬하여 조회합니다.
     *
     * @param companyId 회사 ID
     * @param siteId 사이트 ID
     * @return List<Memo> 메모 목록 (메모 이름 오름차순)
     */
    List<Memo> findByCompanyIdAndSiteIdAndDeleteMarkIsNullOrderByMemoNameAsc(String companyId, String siteId);
}
