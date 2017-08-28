package com.wta.newcloudapp.jiuwei138940.latte_core.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wta.newcloudapp.jiuwei138940.latte_core.app.Latte;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.RestClient;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.ISuccess;
import com.wta.newcloudapp.jiuwei138940.latte_core.ui.recycler.DataConverter;
import com.wta.newcloudapp.jiuwei138940.latte_core.ui.recycler.MultipleRecyclerAdapter;


public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener
,BaseQuickAdapter.RequestLoadMoreListener{
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private final Context mcontext ;


    private RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT,
                           RecyclerView recyclerView,
                           DataConverter converter,
                           PagingBean bean,Context context) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER =converter;
        this.BEAN =bean;
        this.mcontext = context;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView,
                                        DataConverter converter,Context context){
        return new RefreshHandler(swipeRefreshLayout,recyclerView,converter,new PagingBean(),context);
    }

    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }

    public void firstPage(String url){
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .loader(mcontext)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }
    @Override
    public void onRefresh() {
      refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
