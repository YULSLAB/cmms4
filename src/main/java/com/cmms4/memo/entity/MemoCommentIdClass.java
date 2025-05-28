package com.cmms4.memo.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * cmms4 - MemoCommentIdClass
 * 메모 댓글 복합키 클래스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
public class MemoCommentIdClass implements Serializable {
    private String companyId;
    private Integer memoId;
    private Integer commentId;

    // Constructors
    public MemoCommentIdClass() {
    }

    public MemoCommentIdClass(String companyId, Integer memoId, Integer commentId) {
        this.companyId = companyId;
        this.memoId = memoId;
        this.commentId = commentId;
    }

    // Getters and Setters
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getMemoId() {
        return memoId;
    }

    public void setMemoId(Integer memoId) {
        this.memoId = memoId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoCommentIdClass that = (MemoCommentIdClass) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(memoId, that.memoId) &&
               Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, memoId, commentId);
    }
}
