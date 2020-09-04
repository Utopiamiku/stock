package com.miku.vo;

/**
 * @author Utopiamiku
 * @date 2020/8/12 22:29
 * @File ClientInfo.py
 */
public class ClientInfo {
    private Integer clientId;
    private String  clientName;
    private String  contact;
    private String  contactPhoneNum;
    private String  clientAdress;
    private String  remark;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhoneNum() {
        return contactPhoneNum;
    }

    public void setContactPhoneNum(String contactPhoneNum) {
        this.contactPhoneNum = contactPhoneNum;
    }

    public String getClientAdress() {
        return clientAdress;
    }

    public void setClientAdress(String clientAdress) {
        this.clientAdress = clientAdress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ClientInfo(Integer clientId, String clientName, String contact, String contactPhoneNum, String clientAdress, String remark) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.contact = contact;
        this.contactPhoneNum = contactPhoneNum;
        this.clientAdress = clientAdress;
        this.remark = remark;
    }

    public ClientInfo() {
    }
}
