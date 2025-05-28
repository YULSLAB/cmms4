package com.cmms4.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * cmms4 - Memo
 * 메모 관리 엔티티
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Getter
@Setter
@Entity
@Table(name = "memo")
@IdClass(MemoIdClass.class)
public class Memo {

    @Id
    @Column(name = "companyId", length = 5, nullable = false)
    private String companyId;

    @Id
    @Column(name = "memoId", nullable = false)
    private Integer memoId;

    @Column(name = "memoName", length = 100)
    private String memoName;

    @Column(name = "notes", length = 200)
    private String notes;

    @Column(name = "isPinned", length = 1)
    private String isPinned;

    @Column(name = "viewCount")
    private Integer viewCount;

    @Column(name = "siteId", length = 5)
    private String siteId;

    @Column(name = "fileGroupId", length = 10)
    private String fileGroupId;

    @Column(name = "createBy", length = 5)
    private String createBy;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "updateBy", length = 5)
    private String updateBy;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @Column(name = "deleteMark", length = 1)
    private String deleteMark;

    // Constructors
    public Memo() {
    }

    public Memo(String companyId, Integer memoId) {
        this.companyId = companyId;
        this.memoId = memoId;
    }

    public Memo(String companyId, Integer memoId, String memoName) {
        this.companyId = companyId;
        this.memoId = memoId;
        this.memoName = memoName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memo memo = (Memo) o;
        return Objects.equals(companyId, memo.companyId) &&
               Objects.equals(memoId, memo.memoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, memoId);
    }
}
