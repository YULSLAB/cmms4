package com.cmms4.inspection.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * cmms4 - InspectionSchedule
 * 점검 일정 관리 엔티티
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Entity
@Table(name = "inspectionSchedule")
@IdClass(InspectionScheduleIdClass.class) // This will need InspectionScheduleIdClass in the same package or imported
@Getter
@Setter
@NoArgsConstructor
public class InspectionSchedule {

    @Id
    @Column(name = "companyId", length = 5, nullable = false)
    private String companyId;

    @Id
    @Column(name = "inspectionId", length = 10, nullable = false)
    private Integer inspectionId;

    @Id
    @Column(name = "scheduleId", length = 2, nullable = false)
    private Integer scheduleId;

    @Column(name = "frequency", length = 5)
    private String frequency;

    @Column(name = "scheduleDate")
    private LocalDateTime scheduleDate;

    @Column(name = "executeDate")
    private LocalDateTime executeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "companyId", referencedColumnName = "companyId", insertable = false, updatable = false),
        @JoinColumn(name = "inspectionId", referencedColumnName = "inspectionId", insertable = false, updatable = false)
    })
    private Inspection inspection; // This will need Inspection in the same package or imported

    @OneToMany(mappedBy = "inspectionSchedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InspectionItem> items; // This will need InspectionItem in the same package or imported

    // equals and hashCode (only for PK fields)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionSchedule that = (InspectionSchedule) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(inspectionId, that.inspectionId) &&
               Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, inspectionId, scheduleId);
    }
}
