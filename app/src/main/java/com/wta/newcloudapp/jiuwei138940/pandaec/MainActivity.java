package com.wta.newcloudapp.jiuwei138940.pandaec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wta.newcloudapp.jiuwei138940.latte_core.activies.ProxyActivity;
import com.wta.newcloudapp.jiuwei138940.latte_core.delegates.LatteDelegate;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.RestClient;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.ISuccess;

public class MainActivity extends ProxyActivity{

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
