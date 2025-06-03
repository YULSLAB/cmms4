package com.cmms4.memo.controller;

import com.cmms4.memo.entity.Memo;
import com.cmms4.memo.entity.MemoComment;
import com.cmms4.memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;   
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;

import jakarta.servlet.http.HttpSession;

import java.util.List;

/**
 * cmms4 - MemoController
 * 메모 컨트롤러
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Controller
@RequestMapping("/memo")
public class MemoController {

    @Autowired
    private MemoService memoService;

    /**
     * 메모 목록 화면을 조회합니다.
     * 
     * @param model 모델
     * @param session 세션
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 메모 목록 화면
     */
    @GetMapping("/memoList")
    public String showMemoList(Model model, HttpSession session,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10") int size) {
        // 세션에서 사용자 정보 가져오기
        String companyId = (String) session.getAttribute("companyId");
        String siteId = (String) session.getAttribute("siteId");
        
        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        
        // Page 객체로 메모 목록 조회
        Page<Memo> memoPage = memoService.getMemoListPage(companyId, siteId, pageable);
        model.addAttribute("memoPage", memoPage);
        
        return "memo/memoList";
    }

    /**
     * 메모 상세 화면을 조회합니다.
     * 
     * @param model 모델
     * @param session 세션
     * @param memoId 메모 ID
     * @return 메모 상세 화면
     */
    @GetMapping("/memoDetail/{memoId}")
    public String showMemoDetail(Model model, HttpSession session, @PathVariable Integer memoId) {
        // 세션에서 사용자 정보 가져오기
        String companyId = (String) session.getAttribute("companyId");
        
        memoService.getMemo(companyId, memoId).ifPresent(memo -> {
            model.addAttribute("memo", memo);
            List<MemoComment> commentList = memoService.getMemoCommentList(companyId, memoId);
            model.addAttribute("commentList", commentList);
        });
        
        return "memo/memoDetail";
    }

    /**
     * 메모 등록 화면을 조회합니다.
     * 
     * @return 메모 등록 화면
     */
    @GetMapping("/memoForm")
    public String handleMemoForm() {
        return "memo/memoForm";
    }

    /**
     * 메모를 저장합니다.
     * 
     * @param memo 메모
     * @param session 세션
     * @return 메모 목록 화면으로 리다이렉트
     */
    @PostMapping("/memoSave")
    public String handelMemoSave(@ModelAttribute Memo memo, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        String companyId = (String) session.getAttribute("companyId");
        String siteId = (String) session.getAttribute("siteId");
        String username = (String) session.getAttribute("username");
        // 메모 객체에 정보 설정
        memo.setCompanyId(companyId);
        memo.setSiteId(siteId);
        memo.setCreateBy(username);
            
        // 세션 정보가 없는 경우 처리
        if (companyId == null || siteId == null || username == null) {
            // 로깅 또는 에러 처리
            return "redirect:/login";
        }     

        // 메모 저장
        memoService.saveMemo(memo, username);
        return "redirect:/memo/memoList";
    }

    /**
     * 메모를 삭제합니다.
     * 
     * @param memoId 메모 ID
     * @param session 세션
     * @return 메모 목록 화면으로 리다이렉트
     */
    @PostMapping("/memoDelete/{memoId}")
    public String handelMemoDelete(@PathVariable Integer memoId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String companyId = (String) session.getAttribute("companyId");
        memoService.deleteMemo(companyId, memoId, username);
        return "redirect:/memo/memoList";
    }

    /**
     * 메모 댓글을 저장합니다.
     * 
     * @param comment 댓글
     * @param session 세션
     * @return 메모 상세 화면으로 리다이렉트
     */
    @PostMapping("/memoComment/save")
    public String handelMemoCommentSave(@ModelAttribute MemoComment comment, HttpSession session) {
        String companyId = (String) session.getAttribute("companyId");
        comment.setCompanyId(companyId);
        memoService.saveMemoComment(comment);
        return "redirect:/memo/memoDetail/" + comment.getMemoId();
    }
} 