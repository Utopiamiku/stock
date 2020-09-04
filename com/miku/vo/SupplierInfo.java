package com.miku.vo;

import java.io.Serializable;

/**
 * @author Utopiamiku
 * @date 2020/8/12 15:23
 * @File SupplierInfo.py
 */
public class SupplierInfo implements Serializable {
    private static final long serialVersionUID = -2614459914063492780L;
    private Integer supplierId;
    private String  supplierName;
    private String  contact;
    private String  contactPhoneNum;
    private String  supplierAddress;
    private String  remark;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhoneNum() {
        return contactPhoneNum;
    }

    public void setContactPhoneNum(String contactPhoneNum) {
        this.contactPhoneNum = contactPhoneNum;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SupplierInfo(Integer supplierId, String supplierName, String contact, String contactPhoneNum, String getSupplierAddress, String remark) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contact = contact;
        this.contactPhoneNum = contactPhoneNum;
        this.supplierAddress = getSupplierAddress;
        this.remark = remark;
    }

    public SupplierInfo() {
    }
}
