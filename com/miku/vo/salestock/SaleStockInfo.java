package com.miku.vo.salestock;

import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/19 11:16
 * @description:
 */
public class SaleStockInfo {
    private String saleStockId;
    private Integer clientId;
    private String clientName;
    private Date createDate;
    //应付金额
    private Double oughtBalance;
    private Double realBalance;
    private Double balance;
    //付款状态
    private String payState;
    private Integer userId;
    private String  userName;
    private String  remark;

    public String getSaleStockId() {
        return saleStockId;
    }

    public void setSaleStockId(String saleStockId) {
        this.saleStockId = saleStockId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public SaleStockInfo(String saleStockId, Integer clientId, String clientName, Date createDate, Double oughtBalance, Double realBalance, Double balance, String payState, Integer userId, String userName, String remark) {
        this.saleStockId = saleStockId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.createDate = createDate;
        this.oughtBalance = oughtBalance;
        this.realBalance = realBalance;
        this.balance = balance;
        this.payState = payState;
        this.userId = userId;
        this.userName = userName;
        this.remark = remark;
    }

    public SaleStockInfo() {
    }
}
