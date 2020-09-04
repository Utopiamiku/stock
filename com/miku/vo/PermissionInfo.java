package com.miku.vo;

import com.miku.dao.BaseDao;

/**
 * @author Utopiamiku
 * @date 2020/8/9 14:03
 * @File MuenParent.py
 */
public class PermissionInfo extends BaseDao {
    //菜单主键
    private Integer menuId;
    private String  menuName;
    //当前菜单所属于哪个父菜单
    private Integer parentId;
    private String  menuUrl;
    //判断是子标签还是父标签
    private Integer menuType;
    private Integer orderNum;
    private String  icon;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public PermissionInfo(Integer menuId, String menuName, Integer parentId, String menuUrl, Integer menuType, Integer orderNum, String icon) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.parentId = parentId;
        this.menuUrl = menuUrl;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.icon = icon;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



    public PermissionInfo() {
    }
}
