package com.xcubelabs.bhanuprasadm.materialdemo.cutomviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by bhanuprasadm on 29/9/15.
 */
public class MyLayout extends FrameLayout {

    private static final String TAG = "MyLayout";

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "dispatchTouchEvent : DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "dispatchTouchEvent : UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "dispatchTouchEvent : MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "dispatchTouchEvent : CANCEL");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "dispatchTouchEvent : POINTER UP "+event.getPointerCount());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "dispatchTouchEvent : POINTER DOWN "+event.getPointerCount());
                break;
            default:
                break;
        }
        boolean b = super.dispatchTouchEvent(event);
        Log.i(TAG, "dispatchTouchEvent RETURNS "+b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "interceptTouchEvent : DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "interceptTouchEvent : UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "interceptTouchEvent : MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "interceptTouchEvent : CANCEL");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "interceptTouchEvent : POINTER UP "+event.getPointerCount());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "interceptTouchEvent : POINTER DOWN "+event.getPointerCount());
                break;
            default:
                break;
        }
        boolean b = super.onInterceptTouchEvent(event);
        Log.i(TAG, "interceptTouchEvent RETURNS "+b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "touchEvent : DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "touchEvent : UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "touchEvent : MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "touchEvent : CANCEL");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "touchEvent : POINTER UP "+event.getPointerCount());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "touchEvent : POINTER DOWN "+event.getPointerCount());
                break;
            default:
                break;
        }
        boolean b = super.onTouchEvent(event);
        Log.i(TAG, "touchEvent RETURNS "+b);
        return b;
    }
}
