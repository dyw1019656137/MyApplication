package com.example.dyw.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.dyw.myapplication.MainActivity;
import com.example.dyw.myapplication.MyApplication;
import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.adapter.ListAdapter;
import com.example.dyw.myapplication.all.CityName;
import com.example.dyw.myapplication.all.Title;
import com.example.dyw.myapplication.fragment.MainFragment;
import com.example.dyw.myapplication.widget.MySideBar;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.dyw.myapplication.all.CityName.city_array;

/**
 * Created by dyw on 2017/7/6.
 */

public class LocationAvtivity extends FragmentActivity implements MySideBar.OnTouchingLetterChangedListener, LocationSource, AMapLocationListener {
    LocationSource.OnLocationChangedListener mListener;
    private AMap aMap;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    private boolean isFirstLoc = true;


    private ArrayList<Integer> flag = new ArrayList<>();
    private ArrayList<String> content = new ArrayList<String>();
    private ListView list;
    private ArrayList<Integer> letterPosition = new ArrayList<Integer>();
    private ListAdapter listAdapter;
    private MySideBar mySliderBar;
    private WindowManager mWindowManager;
    private TextView overlay;
    private android.view.WindowManager.LayoutParams mWindowParams;
    private Handler handler = new Handler();
    private WindowManager windowManager;
    private OverlayThread overlayThread = new OverlayThread();
    private StringBuffer cityName = new StringBuffer();
    private MapView mapView;
    private String cn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_fragment);

        list = (ListView) this.findViewById(R.id.city_list);
        mySliderBar = (MySideBar) this.findViewById(R.id.mysliderbar);
        iniFlag();
        listAdapter = new ListAdapter(Title.title, content, flag, LocationAvtivity.this);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                String cn_click = content.get(position);
                city_array[0][0] = cn_click;
                content.set(0, cn_click);
                listAdapter.notifyDataSetChanged();

                //Toast.makeText(LocationAvtivity.this, content.get(position), Toast.LENGTH_LONG).show();

            }
        });
        mapView = (MapView) findViewById(R.id.map_location);
        aMap = mapView.getMap();
        aMap.setLocationSource(LocationAvtivity.this);
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);

        initOverlay();
        mySliderBar.setOnTouchingLetterChangedListener(this);
    }

    private void iniFlag() {
        int position = 0;
        int index = 0;
        for (int i = 0; i < city_array.length; i++) {
            System.out.println("iiiiiiiii" + i);
            for (int j = 0; j < city_array[i].length; j++) {
                if (j == 0) {
                    flag.add(position);
                    letterPosition.add(index);
                    position++;
                } else {
                    flag.add(-1);
                }

                index++;
                content.add(city_array[i][j]);
            }
        }
    }


    @Override
    public void onTouchingLetterChanged(int s) {
        list.setSelection(letterPosition.get(s + 1));
        overlay.setText(getStringFloatWindow(s));
        overlay.setVisibility(View.VISIBLE);
        handler.removeCallbacks(overlayThread);
        // 延迟一秒后执行，让overlay为不可见
        handler.postDelayed(overlayThread, 1500);

    }
    public String getStringFloatWindow(int s) {
        String content = null;
        if (s == 0) {
            content = "热";
        } else {
            char c = (char) (s + 64);
            content = c + "";
        }
        return content;
    }

    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //获取定位信息
                    cityName.append(aMapLocation.getCity());
                    String cn = String.valueOf(cityName);
                    city_array[0][0] = cn.toString().trim();
                    content.set(0, cn);
                    listAdapter.notifyDataSetChanged();
                    //Toast.makeText(this, cn.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(this, "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("location_gps", city_array[0][0]);
        System.out.println("传入的值："+city_array[0][0]);
        setResult(1,intent);
        finish();
        System.out.println("onBackPressed:finish");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }
}
