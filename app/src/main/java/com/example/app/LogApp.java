package com.example.app;

import com.wangfeixixi.base.BaseApp;
import com.wangfeixixi.logx.LogX;

public class LogApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        LogX.init(this);
    }
}
