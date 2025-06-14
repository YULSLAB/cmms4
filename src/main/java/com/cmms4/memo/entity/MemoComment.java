package com.cmms4.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

/**
 * cmms4 - MemoComment
 * 메모 댓글 엔티티
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Getter
@Setter
@Entity
@Table(name = "memoComment")
@IdClass(MemoCommentIdClass.class)
public class MemoComment {

    @Id
    @Column(name = "companyId", length = 5, nullable = false)
    private String companyId;

    @Id
    @Column(name = "memoId", nullable = false)
    private String memoId;

    @Id
    @Column(name = "commentId", nullable = false)
    private String commentId;

    @Column(name = "notes", length = 200)
    private String notes;

    @Column(name = "parentId")
    private String parentId;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "sortOrder")
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "companyId", referencedColumnName = "companyId", insertable = false, updatable = false),
        @JoinColumn(name = "memoId", referencedColumnName = "memoId", insertable = false, updatable = false)
    })
    private Memo memo;

    // Constructors
    public MemoComment() {
    }

    public MemoComment(String companyId, String memoId, String commentId) {
        this.companyId = companyId;
        this.memoId = memoId;
        this.commentId = commentId;
    }

    public MemoComment(String companyId, String memoId, String commentId, String notes) {
        this.companyId = companyId;
        this.memoId = memoId;
        this.commentId = commentId;
        this.notes = notes;
    }

    // equals and hashCode (only for PK fields)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoComment that = (MemoComment) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(memoId, that.memoId) &&
               Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, memoId, commentId);
    }
}
