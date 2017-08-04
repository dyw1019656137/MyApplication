package com.example.dyw.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dyw on 2017/7/18.
 */

/*
* {
    "data": [
        {
            "password": "dadasdsa",
            "headurl": "31212313",
            "id": 7,
            "phonenum": "1232312313",
            "username": "asdffg",
            "info": "dasdasawa"
        },
        {
            "password": "123",
            "headurl": "12312312",
            "id": 1,
            "phonenum": "123456789",
            "username": "dyw",
            "info": "好帅啊"
        }
    ]
}
 */
public class User implements Serializable{
    private List<UserItem> data;
    public List<UserItem> getData() {
        return data;
    }
    public void setData(List<UserItem> data) {
        this.data = data;
    }
}
