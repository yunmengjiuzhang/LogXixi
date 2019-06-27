package com.example.app.ui;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.app.R;

import java.util.List;

class XixiAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public XixiAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);
    }
}