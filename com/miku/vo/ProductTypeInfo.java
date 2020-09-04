package com.miku.vo;

import java.io.Serializable;

/**
 * @author Utopiamiku
 * @date 2020/8/13 10:55
 * @File ProductTypeInfo.py
 */
public class ProductTypeInfo implements Serializable {

    private static final long serialVersionUID = 7471819875848044161L;
    private Integer typeId;
    private String  typeName;
    private Integer pid;

    public ProductTypeInfo() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public ProductTypeInfo(Integer typeId, String typeName, Integer pid) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.pid = pid;
    }
}
