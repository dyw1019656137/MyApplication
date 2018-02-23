package com.example.dyw.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dyw on 2018/1/24.
 */

public class Houses implements Serializable{
    private int code;
    private String msg;
    private List<HousesItem> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<HousesItem> getData() {
        return data;
    }

    public void setData(List<HousesItem> data) {
        this.data = data;
    }
}
