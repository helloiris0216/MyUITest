package com.helliris.taipei.myuitest;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerHelper implements MainContract.Model {

    private final int timeout = 30;
    public final String URL = "http://google.com";
    public final String sampleUrl = "http://dummy.restapiexample.com/api/v1/employee/1";
    public final String jokesUrl = "https://api.chucknorris.io/jokes/random";


    @Override
    public void request1(DemoCallback callback) {

        OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build();

        final Request request = new Request.Builder()
                .url(jokesUrl)
                .build();

        try {

            Response response = mHttpClient.newCall(request).execute();

            if (response.body() == null) {

                callback.onReceive("Get response fail!");

                return;
            }

            String resString = response.body().string();

            callback.onReceive("取得成功!");

        }
        catch (Exception e) {

            callback.onReceive(e.getMessage());

        }

    }

    @Override
    public void getJoke(DemoCallback callback) {

        // 開始http request前計數器+1鎖住testing thread
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

                        // callback執行完成，計數器-1變成0後釋放testing thread
                        Idling.getResource().decrement();

                        Looper.myLooper().quit();

                        return;
                    }

                    try {
                        String jsonString = response.body().string();

                        JsonObject ret = new Gson().fromJson(jsonString, JsonObject.class);

                        String value = ret.get("value").getAsString();

                        callback.onReceive(value );

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
}
