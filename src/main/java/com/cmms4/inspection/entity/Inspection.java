package com.cmms4.inspection.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * cmms4 - Inspection
 * 점검 계획 관리 엔티티
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Entity
@Table(name = "inspection")
@IdClass(InspectionIdClass.class)
@Getter
@Setter
@NoArgsConstructor
public class Inspection {

    @Id
    @Column(name = "companyId", length = 5, nullable = false)
    private String companyId;

    @Id
    @Column(name = "inspectionId", length = 10, nullable = false)
    private Integer inspectionId;

    @Column(name = "inspectionName", length = 100)
    private String inspectionName;

    @Column(name = "plantId", length = 10)
    private Integer plantId;

    @Column(name = "jobType", length = 5)
    private String jobType;

    @Column(name = "performDept", length = 5)
    private String performDept;

    @Column(name = "note", length = 200)
    private String note;

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

    @OneToMany(mappedBy = "inspection", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<InspectionSchedule> schedules = new ArrayList<>();

    @Transient
    private List<InspectionItem> items = new ArrayList<>(); // For form binding

    public Inspection(String companyId, Integer inspectionId) {
        this.companyId = companyId;
        this.inspectionId = inspectionId;
        this.schedules = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public Inspection(String companyId, Integer inspectionId, String inspectionName) {
        this.companyId = companyId;
        this.inspectionId = inspectionId;
        this.inspectionName = inspectionName;
        this.schedules = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inspection that = (Inspection) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(inspectionId, that.inspectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, inspectionId);
    }

    // Getter and Setter for items
    public List<InspectionItem> getItems() {
        return items;
    }

    public void setItems(List<InspectionItem> items) {
        this.items = items;
    }
}
