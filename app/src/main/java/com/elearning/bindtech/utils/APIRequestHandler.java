package com.elearning.bindtech.utils;


import com.elearning.bindtech.BaseActivity;
import com.elearning.bindtech.interfaces.CommonInterface;

public class APIRequestHandler {
    private static final APIRequestHandler instance = new APIRequestHandler();

    public static APIRequestHandler getInstance() {
        return instance;
    }

    public void getOnBordingScreensData(String lastUpdatedOn, final BaseActivity mActivity, boolean shouldShowPro, CommonInterface callBack) {

    }
}
