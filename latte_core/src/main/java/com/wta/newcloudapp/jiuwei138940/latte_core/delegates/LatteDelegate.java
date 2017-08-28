package com.wta.newcloudapp.jiuwei138940.latte_core.delegates;



public abstract class LatteDelegate extends PermissionCheckerDelegate {

    public <T extends LatteDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
