package com.example.dyw.myapplication.model;

import com.example.dyw.myapplication.model.HouseOwnerItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dyw on 2017/7/17.
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

public class HouseOwner implements Serializable {

    private List<HouseOwnerItem> data;
    public List<HouseOwnerItem> getData() {
        return data;
    }

    public void setData(List<HouseOwnerItem> data) {
        this.data = data;
    }



}

