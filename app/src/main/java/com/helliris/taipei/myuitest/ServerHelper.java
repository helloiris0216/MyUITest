package com.helliris.taipei.myuitest;

import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerHelper implements MainContract.Model {

    public static final String URL = "http://google.com";
    private final int timeout = 30;


    @Override
    public void request(DemoCallback callback) {

        OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build();

        JsonObject formBodyJson = new JsonObject();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = RequestBody.create(formBodyJson.toString(), JSON);

        final Request request = new Request.Builder()
                .url(ServerHelper.URL)
                .post(formBody)
                .build();

        try {

            Response response = mHttpClient.newCall(request).execute();

            if (response.body() == null) {

                callback.onFail("Get response fail!");

                return;
            }

            String resString = response.body().string();

            callback.onSuccess("取得成功!");

        }
        catch (Exception e) {

            callback.onFail(e.getMessage());

        }

    }

}
