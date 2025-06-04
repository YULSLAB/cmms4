package com.cmms4.workorder.entity;

import com.cmms4.plantMaster.entity.PlantMaster;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * cmms4 - WorkOrder
 * 작업 오더 관리 엔티티
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@Entity
@Table(name = "workOrder")
@IdClass(WorkOrderIdClass.class) // This will need WorkOrderIdClass in the same package or imported
@Getter
@Setter

public class WorkOrder {

    @Id
    @Column(name = "companyId", length = 5, nullable = false)
    private String companyId;

    @Id
    @Column(name = "orderId", length = 10, nullable = false)
    private Integer orderId;

    @Column(name = "orderName", length = 100)
    private String orderName;

    @Column(name = "plantId", length = 10)
    private Integer plantId;

    @Column(name = "memoId", length = 10)
    private Integer memoId;

    @Column(name = "jobType", length = 5)
    private String jobType;

    @Column(name = "performDept", length = 5)
    private String performDept;

    @Column(name = "scheduleDate")
    private LocalDateTime scheduleDate;

    @Column(name = "scheduleMM", precision = 15, scale = 2)
    private BigDecimal scheduleMM;

    @Column(name = "scheduleCost", precision = 15, scale = 2)
    private BigDecimal scheduleCost;

    @Column(name = "scheduleHSE", length = 100)
    private String scheduleHSE;

    @Column(name = "executeDate")
    private LocalDateTime executeDate;

    @Column(name = "executeMM", precision = 15, scale = 2)
    private BigDecimal executeMM;

    @Column(name = "executeCost", precision = 15, scale = 2)
    private BigDecimal executeCost;

    @Column(name = "executeHSE", length = 100)
    private String executeHSE;

    @Lob // For TEXT type
    @Column(name = "notes")
    private String notes;

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

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkOrderItem> items = new ArrayList<>(); // This will need WorkOrderItem in the same package or imported
    
    // Optional: ManyToOne relationship to PlantMaster
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "companyId", referencedColumnName = "companyId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_WORKORDER_PLANT_COMP")),
        @JoinColumn(name = "plantId", referencedColumnName = "plantId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_WORKORDER_PLANT_ID"))
    })
    private PlantMaster plantMaster;

    // Constructors
    public WorkOrder() {
    }

    // equals and hashCode (only for PK fields)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrder that = (WorkOrder) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, orderId);
    }
}
