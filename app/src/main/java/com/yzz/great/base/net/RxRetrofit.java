package com.yzz.great.base.net;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory;
import com.yzz.great.base.net.converterFactorys.FastJsonConverterFactory;
import com.yzz.great.base.net.interceptor.DownLoadInterceptor;
import com.yzz.great.base.net.interceptor.LogHeaderInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class RxRetrofit {

    /**
     * 无请求头请求
     *
     * @param baseUrl
     * @return
     */
    public static Retrofit init(String baseUrl) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(false)
                .addInterceptor(new LogHeaderInterceptor(true, true)
                ).build();

        // 创建网络请求接口的实例
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new Retrofit2ConverterFactory())
                .baseUrl(baseUrl)
                .client(okHttpClient)//此client是为了打印信息
                .build();
    }

    /**
     * 带请求头请求
     *
     * @param baseUrl   服务器地址
     * @param headerMap 请求头键值对
     * @return
     */
    public static Retrofit init(String baseUrl, final HashMap<String, String> headerMap) {
        if (headerMap == null || headerMap.isEmpty()) {
            return init(baseUrl);
        } else {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LogHeaderInterceptor(true, headerMap, true));

            httpClient.connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES);

            OkHttpClient okHttpClient = httpClient.build();

            return new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(new Retrofit2ConverterFactory())//FastJsonConverterFactory.create() GsonConverterFactory
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .build();
        }
    }

    /**
     * 在子线程中运行,文件下载
     *
     * @param baseUrl
     * @param executorService
     * @return
     */
    public static Retrofit init(String baseUrl, ExecutorService executorService) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .retryOnConnectionFailure(false)
                .addInterceptor(new DownLoadInterceptor()).build();

        // 创建网络请求接口的实例
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create()) //GsonConverterFactory
                .baseUrl(baseUrl)
                .client(okHttpClient)//此client是为了打印信息
                .callbackExecutor(executorService)
                .build();
    }

    /**
     * 无请求头请求 + 日志打印控制
     *
     * @param baseUrl
     * @return
     */
    public static Retrofit init(String baseUrl, boolean isPrintLog) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(false)
                .addInterceptor(new LogHeaderInterceptor(true, isPrintLog)
                ).build();

        // 创建网络请求接口的实例
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new Retrofit2ConverterFactory())
                .baseUrl(baseUrl)
                .client(okHttpClient)//此client是为了打印信息
                .build();
    }

    /**
     * 带请求头请求 + 日志打印控制
     *
     * @param baseUrl   服务器地址
     * @param headerMap 请求头键值对
     * @return
     */
    public static Retrofit init(String baseUrl, final HashMap<String, String> headerMap, boolean isPrintLog) {
        if (headerMap == null || headerMap.isEmpty()) {
            return init(baseUrl, isPrintLog);
        } else {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LogHeaderInterceptor(true, headerMap, isPrintLog));

            httpClient.connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES);

            OkHttpClient okHttpClient = httpClient.build();

            return new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(new Retrofit2ConverterFactory())//FastJsonConverterFactory.create() GsonConverterFactory
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .build();
        }
    }

}
