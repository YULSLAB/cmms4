package com.cmms4.inspection.entity; // << UPDATED PACKAGE

import java.io.Serializable;
import java.util.Objects;

public class InspectionScheduleIdClass implements Serializable {
    private String companyId;
    private String inspectionId;
    private String scheduleId; // DB is CHAR(2)

    // Constructors
    public InspectionScheduleIdClass() {
    }

    public InspectionScheduleIdClass(String companyId, String inspectionId, String scheduleId) {
        this.companyId = companyId;
        this.inspectionId = inspectionId;
        this.scheduleId = scheduleId;
    }

    // Getters and Setters
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionScheduleIdClass that = (InspectionScheduleIdClass) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(inspectionId, that.inspectionId) &&
               Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, inspectionId, scheduleId);
    }
}
