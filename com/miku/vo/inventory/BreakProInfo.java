package com.miku.vo.inventory;

import org.omg.CORBA.INTERNAL;

/**
 * @author: Utopiamiku
 * @date: 2020/8/21 19:05
 * @description:
 */
public class BreakProInfo {
    private Integer id;
    private String djId;
    private String proId;
    private String proName;
    private String unit;
    private String proModel;
    private Double price;
    private Integer proNum;
    private Double totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDjId() {
        return djId;
    }

    public void setDjId(String djId) {
        this.djId = djId;
    }



    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getProNum() {
        return proNum;
    }

    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BreakProInfo() {
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public BreakProInfo(Integer id, String djId, String proId, String proName, String unit, String proModel, Double price, Integer proNum, Double totalPrice) {
        this.id = id;
        this.djId = djId;
        this.proId = proId;
        this.proName = proName;
        this.unit = unit;
        this.proModel = proModel;
        this.price = price;
        this.proNum = proNum;
        this.totalPrice = totalPrice;
    }
}
