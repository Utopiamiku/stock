package com.miku.vo.instock;

/**
 * @author: Utopiamiku
 * @date: 2020/8/17 22:21
 * @description:
 */
public class InStockProInfo {
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
        return inPrice*proNum;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public InStockProInfo(Integer itemId, String inStockId, String proId, String proName, String proUnit, String proModel, Integer typeId, String typeName, Double inPrice, Integer proNum, Double totalPrice) {
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
    }

    public InStockProInfo() {
    }
}
