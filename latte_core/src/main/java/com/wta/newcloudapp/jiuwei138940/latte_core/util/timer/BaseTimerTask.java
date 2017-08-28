package com.wta.newcloudapp.jiuwei138940.latte_core.util.timer;

import java.util.TimerTask;

/**
 * Created by zyx on 2017/8/9.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
          if (mITimerListener !=null){
              mITimerListener.onTimer();
          }
    }
}
