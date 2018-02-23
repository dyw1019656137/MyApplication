package com.example.dyw.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dyw on 2017/7/18.
 */

public class User implements Serializable{
    private List<UserItem> data;
    private String msg;
    private int code;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public List<UserItem> getData() {
        return data;
    }
    public void setData(List<UserItem> data) {
        this.data = data;
    }
}
