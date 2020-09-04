package com.miku.vo.salestock;

import java.io.Serializable;

/**
 * @author: Utopiamiku
 * @date: 2020/8/21 16:44
 * @description:
 */
public class SaleReturnProInfo implements Serializable {
    private static final long serialVersionUID = -5860076437385592202L;

    private Integer itemId;
    private String saleStockId;
    private String proId;
    private String proName;
    private String proUnit;
    private String proModel;
    private Integer typeId;
    private String typeName;
    private Double salePrice;
    private Integer proNum;
    private Double totalPrice;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getSaleStockId() {
        return saleStockId;
    }

    public void setSaleStockId(String saleStockId) {
        this.saleStockId = saleStockId;
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

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
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

    public SaleReturnProInfo(Integer itemId, String saleStockId, String proId, String proName, String proUnit, String proModel, Integer typeId, String typeName, Double salePrice, Integer proNum, Double totalPrice) {
        this.itemId = itemId;
        this.saleStockId = saleStockId;
        this.proId = proId;
        this.proName = proName;
        this.proUnit = proUnit;
        this.proModel = proModel;
        this.typeId = typeId;
        this.typeName = typeName;
        this.salePrice = salePrice;
        this.proNum = proNum;
        this.totalPrice = totalPrice;
    }

    public SaleReturnProInfo() {
    }
}
