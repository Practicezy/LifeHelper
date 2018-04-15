package com.example.r.lifehelper;

import android.view.MotionEvent;
import android.view.View;

public class onDubleClickListener implements View.OnTouchListener {
    private DoubleClickCallback mCallback;
    /*点击次数*/
    private int count = 0;
    /*第一次点击时间*/
    private int firClick = 0;
    /*第二次点击时间*/
    private int secClick = 0;

    /*两次点击间隔时间*/
    private final int interval = 1500;

    public interface DoubleClickCallback{
        void onDoubleClick();
    }

    public onDubleClickListener(DoubleClickCallback callback) {
        mCallback = callback;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (MotionEvent.ACTION_DOWN == motionEvent.getAction()){
            count++;
            if ( 1 == count){
                firClick = (int) System.currentTimeMillis();
            }else if ( 2 == count){
                secClick = (int) System.currentTimeMillis();
                if (secClick - firClick < interval){
                    if (mCallback != null){
                        mCallback.onDoubleClick();
                    }

                    count = 0;
                    firClick = 0;
                }else {
                    firClick = secClick;
                    count = 1;
                }
                secClick = 0;
            }
        }
        return true;
    }
}
