package com.example.dyw.myapplication.net;

/**
 * Created by dyw on 2017/7/11.
 */

public class Net {
    public final static String ip = "http://192.168.43.83:8080/House/";
    public final static String showHouseOwnerIP = ip+"houseOwner/houseOwnerAction!showHouseOwner";
    public final static String saveFeedbackIP = ip+"feedback/feedbackAction!saveFeedback";
    public final static String saveHouseOwner = ip+"houseOwner/houseOwnerAction!saveHouseOwner";
    public final static String searchHouseOwner = ip+"houseOwner/houseOwnerAction!searchHouseOwnerAll";
    public final static String saveUser = ip+"user/userAction!saveUser";
    public final static String showUser = ip+"user/userAction!showUser";
    public final static String updateUser = ip+"user/userAction!updateUser";
}
