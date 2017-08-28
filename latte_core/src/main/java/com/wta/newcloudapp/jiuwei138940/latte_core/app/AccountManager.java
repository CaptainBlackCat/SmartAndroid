package com.wta.newcloudapp.jiuwei138940.latte_core.app;

import com.wta.newcloudapp.jiuwei138940.latte_core.util.storage.LattePreference;



public class AccountManager {
    private enum SignTag{
        SIGN_TAG
    }

    //保存用户登录状态，登陆后调用
    public static void setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    //返回用户登录状态
    private static boolean isSignIn(){
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){
            checker.onSignIn();
        }else {
            checker.onNotSignIn();
        }
    }
}
