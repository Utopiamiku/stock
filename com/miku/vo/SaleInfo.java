package com.miku.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/23 17:46
 * @description:
 */


public class SaleInfo implements Serializable {
    private static final long serialVersionUID = -5116616632155575796L;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");

    private Date creatDate;
    private Double salePrice;
    private Double costPrice;
    private Double profit;
    private String sCreatDate;

    public String getmCreatDate() {
        String format = sdf2.format(creatDate);
        return format;
    }

    public void setmCreatDate(String mCreatDate) {
        this.mCreatDate = mCreatDate;
    }

    private String mCreatDate;


    public String getsCreatDate() {
        String format = sdf.format(creatDate);
        return format;
    }

    public void setsCreatDate(String sCreatDate) {
        this.sCreatDate = sCreatDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public SaleInfo(Date creatDate, Double salePrice, Double costPrice, Double profit) {
        this.creatDate = creatDate;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.profit = profit;
    }

    public SaleInfo() {
    }
}
