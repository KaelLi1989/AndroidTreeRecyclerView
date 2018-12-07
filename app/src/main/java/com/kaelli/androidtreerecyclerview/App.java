package com.kaelli.androidtreerecyclerview;

import android.app.Application;

/**
 * Created by KaelLi on 2018/11/26.
 */
public class App extends Application {
    private static App mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static synchronized App getInstance() {
        return mApp;
    }
}
