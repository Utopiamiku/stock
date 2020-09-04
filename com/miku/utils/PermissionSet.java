package com.miku.utils;

/**
 * @author Utopiamiku
 * @date 2020/8/14 22:11
 * @File PermissionSet.py
 */
public class PermissionSet {
    private int id;
    private int pId;
    private String name;
    private boolean open;
    private boolean checked;

    public PermissionSet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public PermissionSet(int id, int pId, String name, boolean open, boolean checked) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.checked = checked;
    }
}
