package com.cmms4.workorder.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * cmms4 - WorkOrderItemIdClass
 * 작업 오더 항목 복합키 클래스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
public class WorkOrderItemIdClass implements Serializable {
    private String companyId;
    private String orderId;
    private String itemId; // DB is CHAR(2)

    // Constructors
    public WorkOrderItemIdClass() {
    }

    public WorkOrderItemIdClass(String companyId, String orderId, String itemId) {
        this.companyId = companyId;
        this.orderId = orderId;
        this.itemId = itemId;
    }

    // Getters and Setters
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrderItemIdClass that = (WorkOrderItemIdClass) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(orderId, that.orderId) &&
               Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, orderId, itemId);
    }
}
