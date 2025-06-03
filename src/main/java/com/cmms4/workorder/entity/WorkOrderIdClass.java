package com.cmms4.workorder.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * cmms4 - WorkOrderIdClass
 * 작업 오더 복합키 클래스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
public class WorkOrderIdClass implements Serializable {
    private String companyId;
    private Integer orderId;

    // Constructors
    public WorkOrderIdClass() {
    }

    public WorkOrderIdClass(String companyId, Integer orderId) {
        this.companyId = companyId;
        this.orderId = orderId;
    }

    // Getters and Setters
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrderIdClass that = (WorkOrderIdClass) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, orderId);
    }
}
