package com.wta.newcloudapp.jiuwei138940.pandaec;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.wta.newcloudapp.jiuwei138940.latte_core.app.Latte;



public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withApiHost("http://127.0.0.1/")
                .configure();
    }
}
