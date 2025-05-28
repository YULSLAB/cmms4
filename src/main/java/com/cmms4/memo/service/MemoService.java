package com.cmms4.memo.service;

import com.cmms4.memo.entity.Memo;
import com.cmms4.memo.entity.MemoComment;
import com.cmms4.memo.repository.MemoRepository;
import com.cmms4.memo.repository.MemoCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * cmms4 - MemoService
 * 메모 서비스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Service
public class MemoService {

    @Autowired
    private MemoRepository memoRepository;

    @Autowired
    private MemoCommentRepository memoCommentRepository;

    /**
     * 메모 목록을 조회합니다.
     * 
     * @param companyId 회사 ID
     * @param siteId 사이트 ID
     * @return 메모 목록
     */
    public List<Memo> getMemoList(String companyId, String siteId) {
        return memoRepository.findByCompanyIdAndSiteIdAndDeleteMarkIsNull(companyId, siteId);
    }

    /**
     * 메모를 조회합니다.
     * 
     * @param companyId 회사 ID
     * @param memoId 메모 ID
     * @return 메모
     */
    public Optional<Memo> getMemo(String companyId, Integer memoId) {
        return memoRepository.findByCompanyIdAndMemoIdAndDeleteMarkIsNull(companyId, memoId);
    }

    /**
     * 메모를 저장합니다.
     * 
     * @param memo 메모
     * @param username 사용자 ID
     * @return 저장된 메모
     */
    @Transactional
    public Memo saveMemo(Memo memo, String username) {
        LocalDateTime now = LocalDateTime.now();
        
        if (memo.getMemoId() == null) {
            // 신규 등록
            memo.setCreateBy(username);
            memo.setCreateDate(now);
            memo.setDeleteMark( null);
            memo.setViewCount(0);
        } else {
            // 수정
            memo.setUpdateBy(username);
            memo.setUpdateDate(now);
        }
        
        return memoRepository.save(memo);
    }

    /**
     * 메모를 삭제합니다.
     * 
     * @param companyId 회사 ID
     * @param memoId 메모 ID
     * @param username 사용자 ID
     */
    @Transactional
    public void deleteMemo(String companyId, Integer memoId, String username) {
        Optional<Memo> memoOpt = memoRepository.findByCompanyIdAndMemoIdAndDeleteMarkIsNull(companyId, memoId);
        if (memoOpt.isPresent()) {
            Memo memo = memoOpt.get();
            memo.setDeleteMark( "Y");
            memo.setUpdateBy(username);
            memo.setUpdateDate(LocalDateTime.now());
            memoRepository.save(memo);
        }
    }

    /**
     * 메모 댓글 목록을 조회합니다.
     * 
     * @param companyId 회사 ID
     * @param memoId 메모 ID
     * @return 댓글 목록
     */
    public List<MemoComment> getMemoCommentList(String companyId, Integer memoId) {
        return memoCommentRepository.findByCompanyIdAndMemoIdOrderBySortOrderAsc(companyId, memoId);
    }

    /**
     * 메모 댓글을 저장합니다.
     * 
     * @param comment 댓글
     * @return 저장된 댓글
     */
    @Transactional
    public MemoComment saveMemoComment(MemoComment comment) {
        return memoCommentRepository.save(comment);
    }
} 