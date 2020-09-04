package com.miku.vo.inventory;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/21 19:01
 * @description:
 */
public class BreakageInfo implements Serializable {
    private static final long serialVersionUID = 4024427076046502909L;

    private String id;
    private Date creatDate;
    private Integer userId;
    private String userName;
    private String remark;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BreakageInfo(String id, Date creatDate, Integer userId, String userName, String remark) {
        this.id = id;
        this.creatDate = creatDate;
        this.userId = userId;
        this.userName = userName;
        this.remark = remark;
    }

    public BreakageInfo() {
    }
}
