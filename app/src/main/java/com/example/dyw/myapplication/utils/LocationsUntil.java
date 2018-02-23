package com.example.dyw.myapplication.utils;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.maps.model.LatLng;

/**
 * Created by dyw on 2018/1/24.
 */

public class LocationsUntil {
    static String district;
    static String street;
    static String streetNum;
    //声明AMapLocationClientOption对象
    public static AMapLocationClientOption mLocationOption = null;
    public static AMapLocationClient mLocationClient = null;

    //返回城区信息
    public static String getDistrict(double longitude, double latitude, Context context){
//        ((Activity)context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
                //初始化AMapLocationClientOption对象
                mLocationOption = new AMapLocationClientOption();
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
                mLocationOption.setOnceLocation(true);
                mLocationOption.setNeedAddress(true);
                mLocationOption.setLocationCacheEnable(false);
                mLocationClient.setLocationListener(mAMapLocationListener);
                mLocationClient.startLocation();
//            }
//        });
        return district;
    }

    //返回街道信息
    public static String getStreet(){

        return street;
    }

    //返回街道门牌号信息
    public static String getStreetNum(){

        return streetNum;
    }

    public static AMapLocationListener mAMapLocationListener = new AMapLocationListener(){
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    district = amapLocation.getDistrict();
                    street = amapLocation.getStreet();
                    streetNum = amapLocation.getStreetNum();
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
}
