package com.aug.androidtest.service;

interface IServiceCallback {
    void startActivity(String actionName,in Bundle bundle);  
    String getResult(String payResult,String payDetailInfomation,in Bundle bundle);
}
