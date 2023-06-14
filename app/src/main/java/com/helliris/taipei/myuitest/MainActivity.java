package com.helliris.taipei.myuitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        button = findViewById(R.id.button);
        button.setText("進入下一頁");
        button.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);

        });


        // TODO: 2023/6/13 測試 Local server
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                ServerHelper server = new ServerHelper();
//
//                server.request(new MainContract.Model.DemoCallback() {
//                    @Override
//                    public void onSuccess(String response) {
//
//                        runOnUiThread(() -> textView.setText(response));
//
//                    }
//
//                    @Override
//                    public void onFail(String err) {
//
//                        runOnUiThread(() -> textView.setText(err));
//
//                    }
//                });
//
//            }
//        });

    }


    @Override
    public void receivedUserName(String name) {
        textView.setText(name);
    }

}