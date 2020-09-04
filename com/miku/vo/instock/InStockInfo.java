package com.miku.vo.instock;

import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/17 22:20
 * @description:
 */
public class InStockInfo {
    private String inStockId;
    private Integer supplierId;
    private String supplierName;
    private Date createDate;
    //应付金额
    private Double oughtBalance;
    private Double realBalance;
    //付款状态
    private String payState;
    private Integer userId;
    private String userName;
    private String remark;

    public String getInStockId() {
        return inStockId;
    }

    public void setInStockId(String inStockId) {
        this.inStockId = inStockId;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getOughtBalance() {
        return oughtBalance;
    }

    public void setOughtBalance(Double oughtBalance) {
        this.oughtBalance = oughtBalance;
    }

    public Double getRealBalance() {
        return realBalance;
    }

    public void setRealBalance(Double realBalance) {
        this.realBalance = realBalance;
    }

    public String getPayState() {

        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public InStockInfo(String inStockId, Integer supplierId, String supplierName, Date createDate, Double oughtBalance, Double realBalance, String payState, Integer userId, String userName, String remark) {
        this.inStockId = inStockId;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.createDate = createDate;
        this.oughtBalance = oughtBalance;
        this.realBalance = realBalance;
        this.payState = payState;
        this.userId = userId;
        this.userName = userName;
        this.remark = remark;
    }

    public InStockInfo() {
    }
}
