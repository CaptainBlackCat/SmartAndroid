package com.wta.newcloudapp.jiuwei138940.pandaec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.wta.newcloudapp.jiuwei138940.latte_core.delegates.LatteDelegate;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.RestClient;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.ISuccess;


public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("http://www.baidu.com")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }
}
