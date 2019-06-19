package com.example.app;

import android.widget.TextView;

import com.wangfeixixi.base.fram.BaseDelegate;

public class FileDelegate extends BaseDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.file_activity;
    }

    @Override
    public void initWidget() {
        super.initWidget();

    }

    public void setLog(String log) {
        TextView view = get(R.id.tv);
        view.setText(log);




    }


}
