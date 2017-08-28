package com.wta.newcloudapp.jiuwei138940.latte_core.delegates.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;


import com.wta.newcloudapp.jiuwei138940.latte_core.R;
import com.wta.newcloudapp.jiuwei138940.latte_core.delegates.LatteDelegate;

import static java.security.AccessController.getContext;



public abstract class BottomItemDelegate extends LatteDelegate implements View.OnKeyListener{
    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View rootview = getView();
        if (rootview != null){
            rootview.setFocusableInTouchMode(true);
            rootview.requestFocus();
            rootview.setOnKeyListener(this);
        }
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK&&event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis()-mExitTime)>EXIT_TIME){
                Toast.makeText(getContext(),"双击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else {
                _mActivity.finish();
                if (mExitTime != 0){
                    mExitTime = 0;
                }
            }
            return true;
        }
        return true;
    }
}
