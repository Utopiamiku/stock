package com.miku.vo.instock;

import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/23 15:16
 * @description:
 */
public class StatisticsProInfo {
    private Integer itemId;
    private String inStockId;
    private String proId;
    private String proName;
    private String proUnit;
    private String proModel;
    private Integer typeId;
    private String typeName;
    private Double inPrice;
    private Integer proNum;
    private Double totalPrice;
    private String name;
    private Date   date;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getInStockId() {
        return inStockId;
    }

    public void setInStockId(String inStockId) {
        this.inStockId = inStockId;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProUnit() {
        return proUnit;
    }

    public void setProUnit(String proUnit) {
        this.proUnit = proUnit;
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getInPrice() {
        return inPrice;
    }

    public void setInPrice(Double inPrice) {
        this.inPrice = inPrice;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StatisticsProInfo(Integer itemId, String inStockId, String proId, String proName, String proUnit, String proModel, Integer typeId, String typeName, Double inPrice, Integer proNum, Double totalPrice, String name, Date date) {
        this.itemId = itemId;
        this.inStockId = inStockId;
        this.proId = proId;
        this.proName = proName;
        this.proUnit = proUnit;
        this.proModel = proModel;
        this.typeId = typeId;
        this.typeName = typeName;
        this.inPrice = inPrice;
        this.proNum = proNum;
        this.totalPrice = totalPrice;
        this.name = name;
        this.date = date;
    }

    public StatisticsProInfo() {
    }
}
