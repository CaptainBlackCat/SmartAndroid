package com.wta.newcloudapp.jiuwei138940.latte_core.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;



public final class Latte {

   public static Configurator init(Context context){
        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT,context.getApplicationContext());
       return Configurator.getInstance();
   }

   public static HashMap<Object,Object> getConfigurations(){
       return Configurator.getInstance().getLatteConfigs();
   }



    public static Configurator getConfiguratior(){
        return Configurator.getInstance();
    }
    public static <T> T getConfiguration(Object key) {
        return getConfiguratior().getConfiguration(key);
    }




   public static Context getApplicationContext(){
       return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONTEXT);
   }
    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }
}
