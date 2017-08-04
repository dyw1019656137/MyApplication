package com.example.dyw.myapplication.model;

import com.example.dyw.myapplication.DB.DBSupport;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by dyw on 2017/7/13.
 */

public class RecentHouseOwner extends DataSupport{

    //12
    private int id;
    private int recentHouseOwnerID;
    private String recentHouseOwnerTitle;
    private String recentHouseOwnerHow;
    private String recentHouseOwnerDevice;
    private String recentHouseOwnerInfo;
    private String recentHouseOwnerLocation;
    private String recentHouseOwnerName;
    private String recentHouseOwnerPhone;
    private String recentHouseOwnerContent;
    private String recentHouseOwnerPicUrl1;
    private String recentHouseOwnerPicUrl2;
    private String recentHouseOwnerPicUrl3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecentHouseOwnerID() {
        return recentHouseOwnerID;
    }

    public void setRecentHouseOwnerID(int recentHouseOwnerID) {
        this.recentHouseOwnerID = recentHouseOwnerID;
    }

    public String getRecentHouseOwnerTitle() {
        return recentHouseOwnerTitle;
    }

    public void setRecentHouseOwnerTitle(String recentHouseOwnerTitle) {
        this.recentHouseOwnerTitle = recentHouseOwnerTitle;
    }

    public String getRecentHouseOwnerHow() {
        return recentHouseOwnerHow;
    }

    public void setRecentHouseOwnerHow(String recentHouseOwnerHow) {
        this.recentHouseOwnerHow = recentHouseOwnerHow;
    }

    public String getRecentHouseOwnerDevice() {
        return recentHouseOwnerDevice;
    }

    public void setRecentHouseOwnerDevice(String recentHouseOwnerDevice) {
        this.recentHouseOwnerDevice = recentHouseOwnerDevice;
    }

    public String getRecentHouseOwnerInfo() {
        return recentHouseOwnerInfo;
    }

    public void setRecentHouseOwnerInfo(String recentHouseOwnerInfo) {
        this.recentHouseOwnerInfo = recentHouseOwnerInfo;
    }

    public String getRecentHouseOwnerLocation() {
        return recentHouseOwnerLocation;
    }

    public void setRecentHouseOwnerLocation(String recentHouseOwnerLocation) {
        this.recentHouseOwnerLocation = recentHouseOwnerLocation;
    }

    public String getRecentHouseOwnerName() {
        return recentHouseOwnerName;
    }

    public void setRecentHouseOwnerName(String recentHouseOwnerName) {
        this.recentHouseOwnerName = recentHouseOwnerName;
    }

    public String getRecentHouseOwnerPhone() {
        return recentHouseOwnerPhone;
    }

    public void setRecentHouseOwnerPhone(String recentHouseOwnerPhone) {
        this.recentHouseOwnerPhone = recentHouseOwnerPhone;
    }

    public String getRecentHouseOwnerContent() {
        return recentHouseOwnerContent;
    }

    public void setRecentHouseOwnerContent(String recentHouseOwnerContent) {
        this.recentHouseOwnerContent = recentHouseOwnerContent;
    }

    public String getRecentHouseOwnerPicUrl1() {
        return recentHouseOwnerPicUrl1;
    }

    public void setRecentHouseOwnerPicUrl1(String recentHouseOwnerPicUrl1) {
        this.recentHouseOwnerPicUrl1 = recentHouseOwnerPicUrl1;
    }

    public String getRecentHouseOwnerPicUrl2() {
        return recentHouseOwnerPicUrl2;
    }

    public void setRecentHouseOwnerPicUrl2(String recentHouseOwnerPicUrl2) {
        this.recentHouseOwnerPicUrl2 = recentHouseOwnerPicUrl2;
    }

    public String getRecentHouseOwnerPicUrl3() {
        return recentHouseOwnerPicUrl3;
    }

    public void setRecentHouseOwnerPicUrl3(String recentHouseOwnerPicUrl3) {
        this.recentHouseOwnerPicUrl3 = recentHouseOwnerPicUrl3;
    }

}
