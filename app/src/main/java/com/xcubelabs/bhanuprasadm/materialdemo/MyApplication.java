package com.xcubelabs.bhanuprasadm.materialdemo;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static final String ROTTEN_TOMATOES_API_KEY = "54wzfswsa4qmjg8hjwa64d4c";
    private static MyApplication sInstance = null;

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
