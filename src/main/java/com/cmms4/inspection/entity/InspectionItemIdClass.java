package com.cmms4.inspection.entity; // << UPDATED PACKAGE

import java.io.Serializable;
import java.util.Objects;

public class InspectionItemIdClass implements Serializable {
    private String companyId;
    private Integer inspectionId;
    private Integer scheduleId;
    private Integer itemId; // DB is CHAR(2)

    // Constructors
    public InspectionItemIdClass() {
    }

    public InspectionItemIdClass(String companyId, Integer inspectionId, Integer scheduleId, Integer itemId) {
        this.companyId = companyId;
        this.inspectionId = inspectionId;
        this.scheduleId = scheduleId;
        this.itemId = itemId;
    }

    // Getters and Setters
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Integer inspectionId) {
        this.inspectionId = inspectionId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionItemIdClass that = (InspectionItemIdClass) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(inspectionId, that.inspectionId) &&
               Objects.equals(scheduleId, that.scheduleId) &&
               Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, inspectionId, scheduleId, itemId);
    }
}
