package com.yzz.great;

import android.app.Application;

import com.yzz.great.util.exception.ExceptionCaught;

public class App extends Application {

    private static App _instance = new App();

    public static App getInstance() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (_instance == null) {
            _instance = this;
        }

        if (BuildConfig.DEBUG) {
            // showExceptionInfo();
        }
    }

    private void showExceptionInfo() {
        Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        ExceptionCaught exceptionCaughtAdapter = new ExceptionCaught(handler);
        Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtAdapter);
    }

}
