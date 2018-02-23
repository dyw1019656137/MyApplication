package com.example.dyw.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dyw on 2017/7/17.
 */

public class House implements Serializable {
    private int code;
    private String msg;
    private List<HouseItem> data;
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
    public List<HouseItem> getData() {
        return data;
    }

    public void setData(List<HouseItem> data) {
        this.data = data;
    }



}

