package com.wangfeixixi.log.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangfeixixi.log.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XixiContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xixi_file_activity);

        initRecyclerView();

        initData();

        refreshData();
    }

    private void refreshData() {

        String name = getIntent().getStringExtra("name");
        List<String> cvsLogs = XixiFileUtils.getFileContent(new File(name));

        notify(cvsLogs);

        setRefreshing(false);
    }

    private void initData() {
        setSwipeListner(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    public void setSwipeListner(SwipeRefreshLayout.OnRefreshListener listner) {
        android.support.v4.widget.SwipeRefreshLayout view = findViewById(R.id.swipe);
        view.setOnRefreshListener(listner);
    }

    public void setRefreshing(boolean refreshing) {
        android.support.v4.widget.SwipeRefreshLayout view = findViewById(R.id.swipe);
        view.setRefreshing(refreshing);
    }

    public void setOnRefreshListener() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        XixiAdapter adapter = (XixiAdapter) recyclerView.getAdapter();
//        adapter.setUpFetchListener();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(XixiContentActivity.this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        XixiAdapter csvLogAdapter = new XixiAdapter(R.layout.xixi_adapter_log_item, new ArrayList<String>());
        //设置Adapter
        recyclerView.setAdapter(csvLogAdapter);
        //设置分隔线
        //recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void setOnAdapterClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        XixiAdapter adapter = (XixiAdapter) recyclerView.getAdapter();
        adapter.setOnItemClickListener(listener);
    }

    public List<String> getAdapterData() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        XixiAdapter adapter = (XixiAdapter) recyclerView.getAdapter();
        return adapter.getData();
    }

    public void notify(List<String> data) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        XixiAdapter adapter = (XixiAdapter) recyclerView.getAdapter();
        adapter.setNewData(data);
    }
}