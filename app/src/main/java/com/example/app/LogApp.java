package com.example.app;

import com.wangfeixixi.base.BaseApp;
import com.wangfeixixi.logxixi.LogXixi;

public class LogApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        LogXixi.init(this);
    }
}
