package com.helliris.taipei.myuitest.model;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.helliris.taipei.myuitest.BuildConfig;
import com.helliris.taipei.myuitest.Idling;
import com.helliris.taipei.myuitest.base.BaseContract;
import com.helliris.taipei.myuitest.contract.MainContract;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainModel implements MainContract.Model {

    private final int timeout = 30;
    public final String jokesUrl = "/jokes/random";


    @Override
    public void setBaseURL(String baseURL) {

    }

    @Override
    public void request1(DemoCallback callback) {

        // 開始 http request 前計數器 +1 鎖住 testing thread
        Idling.getResource().increment();

        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        new Handler(handlerThread.getLooper()).post(new Runnable() {
            @Override
            public void run() {

                OkHttpClient mHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(timeout, TimeUnit.SECONDS)
                        .writeTimeout(timeout, TimeUnit.SECONDS)
                        .readTimeout(timeout, TimeUnit.SECONDS)
                        .build();

                final Request request = new Request.Builder()
                        .url(jokesUrl)
                        .get()
                        .build();

                try {

                    Response response = mHttpClient.newCall(request).execute();

                    if (response.body() == null) {

                        callback.onReceive("取得失敗");

                        // callback執行完成，計數器 -1 變成 0 後釋放 testing thread
                        Idling.getResource().decrement();

                        Looper.myLooper().quit();

                        return;
                    }

                    try {
                        String jsonString = response.body().string();

                        callback.onReceive(jsonString);

                    }
                    catch (IOException e) {
                        callback.onReceive(e.getMessage());
                        e.printStackTrace();
                    }

                    // callback執行完成，計數器-1變成0後釋放testing thread
                    Idling.getResource().decrement();

                    Looper.myLooper().quit();

                }
                catch (Exception e) {

                    e.printStackTrace();

                    callback.onReceive(e.getMessage());

                    // callback執行完成，計數器-1變成0後釋放testing thread
                    Idling.getResource().decrement();

                    Looper.myLooper().quit();

                }

            }
        });

    }

    @Override
    public void getJoke(BaseContract.onListener<String> callback) {

        // 開始 http request 前計數器 +1 鎖住 testing thread
        Idling.getResource().increment();

        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        new Handler(handlerThread.getLooper()).post(new Runnable() {
            @Override
            public void run() {

                OkHttpClient mHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(timeout, TimeUnit.SECONDS)
                        .writeTimeout(timeout, TimeUnit.SECONDS)
                        .readTimeout(timeout, TimeUnit.SECONDS)
                        .build();

                final Request request = new Request.Builder()
                        .url(BuildConfig.SERVER_URL + jokesUrl)
                        .get()
                        .build();

                try {

                    Response response = mHttpClient.newCall(request).execute();

                    if (response.body() == null) {

                        callback.onFail("取得失敗");

                        // callback 執行完成，計數器 -1 變成 0 後釋放 testing thread
                        Idling.getResource().decrement();

                        Looper.myLooper().quit();

                        return;
                    }

                    try {
                        String jsonString = response.body().string();

                        JsonObject ret = new Gson().fromJson(jsonString, JsonObject.class);

                        String value = ret.get("value").getAsString();

                        callback.onFail(value );

                    }
                    catch (IOException e) {
                        callback.onFail(e.getMessage());
                        e.printStackTrace();
                    }

                    // callback 執行完成，計數器 -1 變成 0 後釋放 testing thread
                    Idling.getResource().decrement();

                    Looper.myLooper().quit();

                }
                catch (Exception e) {

                    e.printStackTrace();

                    callback.onFail(e.getMessage());

                    // callback 執行完成，計數器 -1 變成 0 後釋放 testing thread
                    Idling.getResource().decrement();

                    Looper.myLooper().quit();

                }

            }
        });

    }

}
