package com.helliris.taipei.myuitest.model;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.google.android.material.tabs.TabItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.helliris.taipei.myuitest.BuildConfig;
import com.helliris.taipei.myuitest.Idling;
import com.helliris.taipei.myuitest.User;
import com.helliris.taipei.myuitest.base.BaseContract;
import com.helliris.taipei.myuitest.contract.ListContract;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListModel implements ListContract.Model {

    private static final String TAG = "ListModel";
    private final int timeout = 30;
    private final String _getUsers = "/getUsers";


    @Override
    public void getUsers(BaseContract.onListener<List<User>> callback) {

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
                        .url(BuildConfig.SERVER_URL + _getUsers)
                        .get()
                        .build();

                try {

                    Response response = mHttpClient.newCall(request).execute();

                    if (response.body() == null) {

                        callback.onFail("取得失敗");

                        // callback執行完成，計數器-1變成0後釋放testing thread
                        Idling.getResource().decrement();

                        Looper.myLooper().quit();

                        return;
                    }

                    try {
                        String jsonString = response.body().string();

                        List<User> users = new ArrayList<>();

                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataObject = dataArray.getJSONObject(i);

                            String id = dataObject.getString("id");
                            String name = dataObject.getString("name");
                            int level = dataObject.getInt("level");

                            User user = new User(id, name, level);

                            System.out.println("ID: " + id);
                            System.out.println("Name: " + name);
                            System.out.println("Level: " + level);

                            Log.d(TAG, "User = " + user);

                            users.add(user);
                        }

                        callback.onSuccess(users);

                    }
                    catch (IOException e) {
                        callback.onFail(e.getMessage());
                        e.printStackTrace();
                    }

                    // callback執行完成，計數器-1變成0後釋放testing thread
                    Idling.getResource().decrement();

                    Looper.myLooper().quit();

                }
                catch (Exception e) {

                    e.printStackTrace();

                    callback.onFail(e.getMessage());

                    // callback執行完成，計數器-1變成0後釋放testing thread
                    Idling.getResource().decrement();

                    Looper.myLooper().quit();

                }

            }
        });

    }

}
