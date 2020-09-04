package com.miku.vo;

import java.io.Serializable;

/**
 * @author Utopiamiku
 * @date 2020/8/13 8:55
 * @File ProductInfo.py
 */
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = -3860391476296487538L;

    private String proNo;
    private String proName;
    private String proModel;
    private Integer typeId;
    private String typeName;
    //单位
    private String unit;
    //进价
    private double inPrice;
    //售价
    private double outPrice;
    //成本价
    private double costPrice;
    //商品数量
    private Integer proNum;
    //商品总价
    private double proTotalPrice;
    //商品最低库存
    private Integer minProNum;
    //生产厂商
    private String factory;
    //默认为0；增加到仓库为1,进货状态为2
    private String state;
    private String remark;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getInPrice() {
        return inPrice;
    }

    public void setInPrice(double inPrice) {
        this.inPrice = inPrice;
    }

    public double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(double outPrice) {
        this.outPrice = outPrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getProNum() {
        return proNum;
    }

    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }

    public double getProTotalPrice() {
        return proTotalPrice;
    }

    public void setProTotalPrice(double proTotalPrice) {
        this.proTotalPrice = proTotalPrice;
    }

    public Integer getMinProNum() {
        return minProNum;
    }

    public void setMinProNum(Integer minProNum) {
        this.minProNum = minProNum;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ProductInfo(String proNo, String proName, String proModel, Integer typeId, String typeName, String unit, double inPrice, double outPrice, double costPrice, Integer proNum, double proTotalPrice, Integer minProNum, String factory, String state, String remark) {
        this.proNo = proNo;
        this.proName = proName;
        this.proModel = proModel;
        this.typeId = typeId;
        this.typeName = typeName;
        this.unit = unit;
        this.inPrice = inPrice;
        this.outPrice = outPrice;
        this.costPrice = costPrice;
        this.proNum = proNum;
        this.proTotalPrice = proTotalPrice;
        this.minProNum = minProNum;
        this.factory = factory;
        this.state = state;
        this.remark = remark;
    }

    public ProductInfo() {
    }
}
