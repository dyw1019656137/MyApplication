package com.example.dyw.myapplication.model;

import com.example.dyw.myapplication.DB.DBSupport;

import org.litepal.crud.DataSupport;

/**
 * Created by dyw on 2017/7/13.
 */

public class CollectHouseOwner extends DBSupport<CollectHouseOwner>{
    private int collectHouseOwnerID;
    private int isCollect;  //1:like    0:dislike
    private String collectHouseOwnerTitle;
    private String collectHouseOwnerHow;
    private String collectHouseOwnerDevice;
    private String collectHouseOwnerInfo;
    private String collectHouseOwnerLocation;
    private String collectHouseOwnerName;
    private String collectHouseOwnerPhone;
    private String collectHouseOwnerContent;
    private String collectHouseOwnerPicUrl1;
    private String collectHouseOwnerPicUrl2;
    private String collectHouseOwnerPicUrl3;

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }
    public int getCollectHouseOwnerID() {
        return collectHouseOwnerID;
    }

    public void setCollectHouseOwnerID(int collectHouseOwnerID) {
        this.collectHouseOwnerID = collectHouseOwnerID;
    }

    public String getCollectHouseOwnerTitle() {
        return collectHouseOwnerTitle;
    }

    public void setCollectHouseOwnerTitle(String collectHouseOwnerTitle) {
        this.collectHouseOwnerTitle = collectHouseOwnerTitle;
    }

    public String getCollectHouseOwnerHow() {
        return collectHouseOwnerHow;
    }

    public void setCollectHouseOwnerHow(String collectHouseOwnerHow) {
        this.collectHouseOwnerHow = collectHouseOwnerHow;
    }

    public String getCollectHouseOwnerDevice() {
        return collectHouseOwnerDevice;
    }

    public void setCollectHouseOwnerDevice(String collectHouseOwnerDevice) {
        this.collectHouseOwnerDevice = collectHouseOwnerDevice;
    }

    public String getCollectHouseOwnerInfo() {
        return collectHouseOwnerInfo;
    }

    public void setCollectHouseOwnerInfo(String collectHouseOwnerInfo) {
        this.collectHouseOwnerInfo = collectHouseOwnerInfo;
    }

    public String getCollectHouseOwnerLocation() {
        return collectHouseOwnerLocation;
    }

    public void setCollectHouseOwnerLocation(String collectHouseOwnerLocation) {
        this.collectHouseOwnerLocation = collectHouseOwnerLocation;
    }

    public String getCollectHouseOwnerName() {
        return collectHouseOwnerName;
    }

    public void setCollectHouseOwnerName(String collectHouseOwnerName) {
        this.collectHouseOwnerName = collectHouseOwnerName;
    }

    public String getCollectHouseOwnerPhone() {
        return collectHouseOwnerPhone;
    }

    public void setCollectHouseOwnerPhone(String collectHouseOwnerPhone) {
        this.collectHouseOwnerPhone = collectHouseOwnerPhone;
    }

    public String getCollectHouseOwnerContent() {
        return collectHouseOwnerContent;
    }

    public void setCollectHouseOwnerContent(String collectHouseOwnerContent) {
        this.collectHouseOwnerContent = collectHouseOwnerContent;
    }

    public String getCollectHouseOwnerPicUrl1() {
        return collectHouseOwnerPicUrl1;
    }

    public void setCollectHouseOwnerPicUrl1(String collectHouseOwnerPicUrl1) {
        this.collectHouseOwnerPicUrl1 = collectHouseOwnerPicUrl1;
    }

    public String getCollectHouseOwnerPicUrl2() {
        return collectHouseOwnerPicUrl2;
    }

    public void setCollectHouseOwnerPicUrl2(String collectHouseOwnerPicUrl2) {
        this.collectHouseOwnerPicUrl2 = collectHouseOwnerPicUrl2;
    }

    public String getCollectHouseOwnerPicUrl3() {
        return collectHouseOwnerPicUrl3;
    }

    public void setCollectHouseOwnerPicUrl3(String collectHouseOwnerPicUrl3) {
        this.collectHouseOwnerPicUrl3 = collectHouseOwnerPicUrl3;
    }


}
