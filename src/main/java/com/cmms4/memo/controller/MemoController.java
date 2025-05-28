package com.cmms4.memo.controller;

import com.cmms4.memo.entity.Memo;
import com.cmms4.memo.entity.MemoComment;
import com.cmms4.memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * @param auth 인증 정보
     * @return 메모 목록 화면
     */
    @GetMapping("/memoList")
    public String list(Model model, Authentication auth) {
        String companyId = auth.getName();
        String siteId = auth.getDetails().toString();
        
        List<Memo> memoList = memoService.getMemoList(companyId, siteId);
        model.addAttribute("memoList", memoList);
        
        return "memo/memoList";
    }

    /**
     * 메모 상세 화면을 조회합니다.
     * 
     * @param model 모델
     * @param auth 인증 정보
     * @param memoId 메모 ID
     * @return 메모 상세 화면
     */
    @GetMapping("/memoDetail/{memoId}")
    public String detail(Model model, Authentication auth, @PathVariable Integer memoId) {
        String companyId = auth.getName();
        
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
    public String form() {
        return "memo/memoForm";
    }

    /**
     * 메모를 저장합니다.
     * 
     * @param memo 메모
     * @param auth 인증 정보
     * @return 메모 목록 화면으로 리다이렉트
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Memo memo, Authentication auth) {
        String username = auth.getName();
        memoService.saveMemo(memo, username);
        return "redirect:/memo/memoList";
    }

    /**
     * 메모를 삭제합니다.
     * 
     * @param memoId 메모 ID
     * @param auth 인증 정보
     * @return 메모 목록 화면으로 리다이렉트
     */
    @PostMapping("/delete/{memoId}")
    public String delete(@PathVariable Integer memoId, Authentication auth) {
        String username = auth.getName();
        String companyId = auth.getName();
        memoService.deleteMemo(companyId, memoId, username);
        return "redirect:/memo/memoList";
    }

    /**
     * 메모 댓글을 저장합니다.
     * 
     * @param comment 댓글
     * @param auth 인증 정보
     * @return 메모 상세 화면으로 리다이렉트
     */
    @PostMapping("/comment/save")
    public String saveComment(@ModelAttribute MemoComment comment, Authentication auth) {
        String username = auth.getName();
        comment.setCompanyId(username);
        memoService.saveMemoComment(comment);
        return "redirect:/memo/memoDetail/" + comment.getMemoId();
    }
} 