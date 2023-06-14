package com.helliris.taipei.myuitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private TextView textView;
    private ServerHelper serverHelper;
    private MainPresenter iPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverHelper = new ServerHelper();
        iPresenter = new MainPresenter(this);

        textView = findViewById(R.id.textView);

        // 取得使用者名稱
        Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener(v -> {

            iPresenter.requestUserName();

        });

        // 取得笑話
        Button button2 = findViewById(R.id.button2);

        button2.setOnClickListener(v -> {

            serverHelper.getJoke(new MainContract.Model.DemoCallback() {
                @Override
                public void onReceive(String response) {

                    runOnUiThread(() -> textView.setText(response));

                }

            });

        });

        // 進入下一頁
        Button button3 = findViewById(R.id.button3);

        button3.setOnClickListener(v -> {

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

    /**
     * 測試 MVP
     * @param name user name
     */
    @Override
    public void receivedUserName(String name) {

        runOnUiThread(() -> textView.setText(name));

    }

}