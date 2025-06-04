package com.cmms4.inspection.entity; // << UPDATED PACKAGE

import java.io.Serializable;
import java.util.Objects;

public class InspectionIdClass implements Serializable {
    private String companyId;
    private Integer inspectionId;

    // Constructors
    public InspectionIdClass() {
    }

    public InspectionIdClass(String companyId, Integer inspectionId) {
        this.companyId = companyId;
        this.inspectionId = inspectionId;
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionIdClass that = (InspectionIdClass) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(inspectionId, that.inspectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, inspectionId);
    }
}
