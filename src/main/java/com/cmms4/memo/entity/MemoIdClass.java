package com.cmms4.memo.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * cmms4 - MemoIdClass
 * 메모 복합키 클래스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
public class MemoIdClass implements Serializable {
    private String companyId;
    private Integer memoId;

    // Constructors
    public MemoIdClass() {
    }

    public MemoIdClass(String companyId, Integer memoId) {
        this.companyId = companyId;
        this.memoId = memoId;
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoIdClass that = (MemoIdClass) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(memoId, that.memoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, memoId);
    }
}
