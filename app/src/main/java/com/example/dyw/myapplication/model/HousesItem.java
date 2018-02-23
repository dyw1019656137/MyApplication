package com.example.dyw.myapplication.model;

import java.io.Serializable;

/**
 * Created by dyw on 2018/1/24.
 *
 *
 "data": [{
 "how": "三室一厅",
 "house_id": 39,
 "money": "66666元/月",
 "title": "靠靠靠靠靠靠看看看看看看",
 "picurl1": "http://192.168.43.83:8080/House/houseimage/houseOwnerPic1_a410ca8d-9a87-4f20-bd7e-625ae63e35a4.jpg"
 }]
 *
 *
 */

public class HousesItem  implements Serializable {
    private String how;
    private int house_id;
    private String money;
    private String title;
    private String picurl1;
    private double longitude;
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicurl1() {
        return picurl1;
    }

    public void setPicurl1(String picurl1) {
        this.picurl1 = picurl1;
    }
}
