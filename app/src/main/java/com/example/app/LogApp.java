package com.example.app;

import com.wangfeixixi.base.BaseApp;
import com.wangfeixixi.logx.LogXConfig;

public class LogApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        LogXConfig.init(this);
    }
}
