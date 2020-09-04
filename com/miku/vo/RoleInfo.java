package com.miku.vo;

import java.io.Serializable;

/**
 * @author Utopiamiku
 * @date 2020/8/10 22:53
 * @File RoleInfo.py
 */
public class RoleInfo implements Serializable {
    private static final long serialVersionUID = 6507130395398231671L;
    private Integer roleId;
    private String  roleName;
    private String  remark;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public RoleInfo(Integer roleId, String roleName, String remark) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.remark = remark;
    }

    public RoleInfo() {
    }
}
