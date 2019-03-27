package com.example.schoolchatdemo.UserPackage;

public interface UserCallBackListener {
    void onFinish(String jsonString);
    void onError(Exception e);
}
