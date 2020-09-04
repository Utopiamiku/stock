package com.miku.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Utopiamiku
 * @date 2020/8/8 17:07
 * @File UserInfo.py
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -6644044449840008526L;
    //用户id
    private Integer userId;
    //登录账号
    private String  loginCode;
    //登录密码
    private String  passWord;
    //用户姓名
    private String  userName;
    //所拥有的权限
    private Integer roleId;
    //判断能否登录
    private Integer state;
    //备注
    private String  remark;

    private List<String> roleList;

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public UserInfo() {
    }

    public UserInfo(Integer userId, String loginCode, String passWord, String userName, Integer roleId, Integer state, String remark) {
        this.userId = userId;
        this.loginCode = loginCode;
        this.passWord = passWord;
        this.userName = userName;
        this.roleId = roleId;
        this.state = state;
        this.remark = remark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
