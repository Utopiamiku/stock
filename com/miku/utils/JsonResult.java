package com.miku.utils;

/**
 * @author Utopiamiku
 * @date 2020/8/12 17:53
 * @File JsonResult.py
 */

public class JsonResult<T>{

    private T obj;
    private T obj2;
    private String msg;
    private String msg2;

    public String getMsg2() {
        return msg2;
    }

    public JsonResult(T obj, String msg, int stateCode) {
        this.obj = obj;
        this.msg = msg;
        this.stateCode = stateCode;
    }

    public JsonResult(T obj) {
        this.obj = obj;
    }

    public JsonResult(String msg, int stateCode) {
        this.msg = msg;
        this.stateCode = stateCode;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    private int stateCode;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public T getObj2() {
        return obj2;
    }

    public void setObj2(T obj2) {
        this.obj2 = obj2;
    }

    public JsonResult() {
    }
}
