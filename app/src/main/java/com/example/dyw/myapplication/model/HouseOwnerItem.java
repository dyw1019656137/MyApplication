package com.example.dyw.myapplication.model;

/**
 * Created by dyw on 2017/7/17.
 */

import java.io.Serializable;

/**
 * Created by dyw on 2017/7/10.
 */
/*
{
    "data": [
        {
            "houseOwnerPicurl2": "http://192.168.33.38:8080/House/houseimage/houseOwnerPic2_a410ca8d-9a87-4f20-bd7e-625ae63e35a4.jpg",
            "houseOwnerPicurl3": "http://192.168.33.38:8080/House/houseimage/houseOwnerPic3_a410ca8d-9a87-4f20-bd7e-625ae63e35a4.jpg",
            "houseOwnerName": "帅帅帅",
            "houseOwnerLocation": "啦啦啦啦啦啦了",
            "houseOwnerPhone": "13151567760",
            "houseOwnerPicurl1": "http://192.168.33.38:8080/House/houseimage/houseOwnerPic1_a410ca8d-9a87-4f20-bd7e-625ae63e35a4.jpg",
            "houseOwnerTitle": "靠靠靠靠靠靠看看看看看看",
            "houseOwnerContent": "66666元/月",
            "houseOwnerDevice": "床，电视，看看看看看看",
            "houseOwnerID": 39,
            "houseOwnerInfo": "们嗯嗯嗯",
            "houseOwnerHow": "三室一厅"
        }
    ]
}
* */
public class HouseOwnerItem implements Serializable {
    private String houseOwnerPicurl1;
    private String houseOwnerPicurl2;
    private String houseOwnerPicurl3;
    private String houseOwnerTitle;
    private String houseOwnerContent;
    private String houseOwnerHow;
    private String houseOwnerInfo;
    private String houseOwnerDevice;
    private String houseOwnerLocation;
    private String houseOwnerName;
    private String houseOwnerPhone;
    private String houseOwnerID;

    public String getHouseOwnerID() {
        return houseOwnerID;
    }

    public void setHouseOwnerID(String houseOwnerID) {
        this.houseOwnerID = houseOwnerID;
    }
    public String getHouseOwnerName() {
        return houseOwnerName;
    }

    public void setHouseOwnerName(String houseOwnerName) {
        this.houseOwnerName = houseOwnerName;
    }

    public String getHouseOwnerPhone() {
        return houseOwnerPhone;
    }

    public void setHouseOwnerPhone(String houseOwnerPhone) {
        this.houseOwnerPhone = houseOwnerPhone;
    }
    public String getHouseOwnerHow() {
        return houseOwnerHow;
    }

    public void setHouseOwnerHow(String houseOwnerHow) {
        this.houseOwnerHow = houseOwnerHow;
    }

    public String getHouseOwnerInfo() {
        return houseOwnerInfo;
    }

    public void setHouseOwnerInfo(String houseOwnerInfo) {
        this.houseOwnerInfo = houseOwnerInfo;
    }

    public String getHouseOwnerDevice() {
        return houseOwnerDevice;
    }

    public void setHouseOwnerDevice(String houseOwnerDevice) {
        this.houseOwnerDevice = houseOwnerDevice;
    }

    public String getHouseOwnerLocation() {
        return houseOwnerLocation;
    }

    public void setHouseOwnerLocation(String houseOwnerLocation) {
        this.houseOwnerLocation = houseOwnerLocation;
    }


    public String getHouseOwnerPicurl1() {
        return houseOwnerPicurl1;
    }

    public void setHouseOwnerPicurl1(String houseOwnerPicurl1) {
        this.houseOwnerPicurl1 = houseOwnerPicurl1;
    }

    public String getHouseOwnerPicurl2() {
        return houseOwnerPicurl2;
    }

    public void setHouseOwnerPicurl2(String houseOwnerPicurl2) {
        this.houseOwnerPicurl2 = houseOwnerPicurl2;
    }

    public String getHouseOwnerPicurl3() {
        return houseOwnerPicurl3;
    }

    public void setHouseOwnerPicurl3(String houseOwnerPicurl3) {
        this.houseOwnerPicurl3 = houseOwnerPicurl3;
    }
    public String getHouseOwnerTitle() {
        return houseOwnerTitle;
    }

    public void setHouseOwnerTitle(String houseOwnerTitle) {
        this.houseOwnerTitle = houseOwnerTitle;
    }

    public String getHouseOwnerContent() {
        return houseOwnerContent;
    }

    public void setHouseOwnerContent(String houseOwnerContent) {
        this.houseOwnerContent = houseOwnerContent;
    }



}
