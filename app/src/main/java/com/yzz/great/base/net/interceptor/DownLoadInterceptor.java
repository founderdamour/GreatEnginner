package com.yzz.great.base.net.interceptor;

import android.util.Log;
import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;

import java.io.IOException;

public class DownLoadInterceptor implements Interceptor {

    private static String TAG = "RxRetrofitLog";

    @Override
    public synchronized okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
        // 日志打印封装
        String TAG = "RxRetrofitLog";
        Log.d(TAG, "RxRetrofit > ============================================");
        // 请求地址
        Request request = chain.request();
        Log.d(TAG, "RxRetrofit > 地址: " + request.url());
        // 结果
        Log.d(TAG, "RxRetrofit > ============================================");
        return chain.proceed(chain.request());
    }
}

