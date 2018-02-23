package com.example.dyw.myapplication.net;

/**
 * Created by dyw on 2017/7/11.
 */

public class Net {
    public final static String ip = "http://192.168.100.117:8080/House/";
    //user
    public final static String loginUserIP = ip+"user/userAction!checkUser";
    public final static String saveUserIP = ip+"user/userAction!saveUser";
    public final static String forgetUserIP = ip+"user/userAction!forgetPassword";
    public final static String updateUserIP = ip+"user/userAction!updateUser";
    public final static String showUserIP = ip+"user/userAction!showUser";
    public final static String showUserAllByIdIP = ip+"user/userAction!showUserAllByID";
    //关于我们
    public final static String aboutUsIP = ip+"about/aboutAction!showAbout";
    //浏览历史
    public final static String saveHistoryIP = ip+"history/historyAction!saveHistory";
    public final static String showHistoryIP = ip+"history/historyAction!showHistoryAll";
    public final static String removeHistoryIP = ip+"history/historyAction!removeHistory";
    //收藏
    public final static String saveCollectionIP = ip+"collection/collectionAction!saveCollection";
    public final static String showCollectionIP = ip+"collection/collectionAction!showCollectionAll";
    public final static String removeCollectionIP = ip+"collection/collectionAction!removeCollection";
    //用户反馈
    public final static String saveFeedbackIP = ip+"feedback/feedbackAction!saveFeedback";
    //房屋信息
    public final static String showHouseAllIP = ip+"house/houseAction!showHouseAll";
    public final static String showOwnHouseIP = ip+"house/houseAction!showOwnHouse";
    public final static String showRentHouseIP = ip+"house/houseAction!showRentHouse";
    public final static String showHouseByIDIP = ip+"house/houseAction!showHouseByID";
    public final static String saveHouseIP = ip+"house/houseAction!saveHouse";
    public final static String rentHouseIP = ip+"house/houseAction!rentHouse";
    public final static String removeHouseIP = ip+"house/houseAction!removeHouseOwner";
    public final static String searchHouseIP = ip+"house/houseAction!searchHouseOwnerAll";
    //地址转化
    public final static String locationsIP = "http://api.jisuapi.com/geoconvert/coord2addr";







    public final static String showHouseOwnerIP = ip+"houseOwner/houseOwnerAction!showHouseOwner";
//    public final static String saveFeedbackIP = ip+"feedback/feedbackAction!saveFeedback";
    public final static String saveHouseOwner = ip+"houseOwner/houseOwnerAction!saveHouseOwner";
    public final static String searchHouseOwner = ip+"houseOwner/houseOwnerAction!searchHouseOwnerAll";
    public final static String saveUser = ip+"user/userAction!saveUser";
    public final static String showUser = ip+"user/userAction!showUser";
    public final static String updateUser = ip+"user/userAction!updateUser";
}
