package com.cmms4.memo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cmms4.memo.entity.MemoComment;
import com.cmms4.memo.entity.MemoCommentIdClass;

import java.util.List;

/**
 * cmms4 - MemoCommentRepository
 * 메모 댓글 레포지토리
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Repository
public interface MemoCommentRepository extends JpaRepository<MemoComment, MemoCommentIdClass> {
    
    /**
     * 특정 메모의 댓글 목록을 조회합니다.
     * 
     * @param companyId 회사 ID
     * @param memoId 메모 ID
     * @return 댓글 목록
     */
    List<MemoComment> findByCompanyIdAndMemoIdOrderBySortOrderAsc(String companyId, Integer memoId);
    
    /**
     * 특정 메모의 댓글 수를 조회합니다.
     * 
     * @param companyId 회사 ID
     * @param memoId 메모 ID
     * @return 댓글 수
     */
    long countByCompanyIdAndMemoId(String companyId, Integer memoId);
}
