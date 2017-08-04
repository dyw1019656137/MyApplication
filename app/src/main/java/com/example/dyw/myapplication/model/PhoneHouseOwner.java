package com.example.dyw.myapplication.model;

import com.example.dyw.myapplication.DB.DBSupport;

import org.litepal.crud.DataSupport;

/**
 * Created by dyw on 2017/7/13.
 */

public class PhoneHouseOwner extends DBSupport<PhoneHouseOwner>{
    private int phoneHouseOwnerID;
    private String phoneHouseOwnerTitle;
    private String phoneHouseOwnerHow;
    private String phoneHouseOwnerDevice;
    private String phoneHouseOwnerInfo;
    private String phoneHouseOwnerLocation;
    private String phoneHouseOwnerName;
    private String phoneHouseOwnerPhone;
    private String phoneHouseOwnerContent;
    private String phoneHouseOwnerPicUrl1;
    private String phoneHouseOwnerPicUrl2;
    private String phoneHouseOwnerPicUrl3;

    public int getPhoneHouseOwnerID() {
        return phoneHouseOwnerID;
    }

    public void setPhoneHouseOwnerID(int phoneHouseOwnerID) {
        this.phoneHouseOwnerID = phoneHouseOwnerID;
    }

    public String getPhoneHouseOwnerTitle() {
        return phoneHouseOwnerTitle;
    }

    public void setPhoneHouseOwnerTitle(String phoneHouseOwnerTitle) {
        this.phoneHouseOwnerTitle = phoneHouseOwnerTitle;
    }

    public String getPhoneHouseOwnerHow() {
        return phoneHouseOwnerHow;
    }

    public void setPhoneHouseOwnerHow(String phoneHouseOwnerHow) {
        this.phoneHouseOwnerHow = phoneHouseOwnerHow;
    }

    public String getPhoneHouseOwnerDevice() {
        return phoneHouseOwnerDevice;
    }

    public void setPhoneHouseOwnerDevice(String phoneHouseOwnerDevice) {
        this.phoneHouseOwnerDevice = phoneHouseOwnerDevice;
    }

    public String getPhoneHouseOwnerInfo() {
        return phoneHouseOwnerInfo;
    }

    public void setPhoneHouseOwnerInfo(String phoneHouseOwnerInfo) {
        this.phoneHouseOwnerInfo = phoneHouseOwnerInfo;
    }

    public String getPhoneHouseOwnerLocation() {
        return phoneHouseOwnerLocation;
    }

    public void setPhoneHouseOwnerLocation(String phoneHouseOwnerLocation) {
        this.phoneHouseOwnerLocation = phoneHouseOwnerLocation;
    }

    public String getPhoneHouseOwnerName() {
        return phoneHouseOwnerName;
    }

    public void setPhoneHouseOwnerName(String phoneHouseOwnerName) {
        this.phoneHouseOwnerName = phoneHouseOwnerName;
    }

    public String getPhoneHouseOwnerPhone() {
        return phoneHouseOwnerPhone;
    }

    public void setPhoneHouseOwnerPhone(String phoneHouseOwnerPhone) {
        this.phoneHouseOwnerPhone = phoneHouseOwnerPhone;
    }

    public String getPhoneHouseOwnerContent() {
        return phoneHouseOwnerContent;
    }

    public void setPhoneHouseOwnerContent(String phoneHouseOwnerContent) {
        this.phoneHouseOwnerContent = phoneHouseOwnerContent;
    }

    public String getPhoneHouseOwnerPicUrl1() {
        return phoneHouseOwnerPicUrl1;
    }

    public void setPhoneHouseOwnerPicUrl1(String phoneHouseOwnerPicUrl1) {
        this.phoneHouseOwnerPicUrl1 = phoneHouseOwnerPicUrl1;
    }

    public String getPhoneHouseOwnerPicUrl2() {
        return phoneHouseOwnerPicUrl2;
    }

    public void setPhoneHouseOwnerPicUrl2(String phoneHouseOwnerPicUrl2) {
        this.phoneHouseOwnerPicUrl2 = phoneHouseOwnerPicUrl2;
    }

    public String getPhoneHouseOwnerPicUrl3() {
        return phoneHouseOwnerPicUrl3;
    }

    public void setPhoneHouseOwnerPicUrl3(String phoneHouseOwnerPicUrl3) {
        this.phoneHouseOwnerPicUrl3 = phoneHouseOwnerPicUrl3;
    }


}
