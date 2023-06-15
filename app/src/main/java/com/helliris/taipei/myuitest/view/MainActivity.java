package com.helliris.taipei.myuitest.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.helliris.taipei.myuitest.R;
import com.helliris.taipei.myuitest.base.BaseContract;
import com.helliris.taipei.myuitest.contract.MainContract;
import com.helliris.taipei.myuitest.model.MainModel;
import com.helliris.taipei.myuitest.presenter.MainPresenter;

public class MainActivity extends BaseActivity implements MainContract.View {

    private TextView textView;
    private MainModel mainModel;
    private MainPresenter iPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainModel = new MainModel();
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

            showProgress("載入中...");

            mainModel.getJoke(new BaseContract.onListener<String>() {
                @Override
                public void onFail(String message) {

                    runOnUiThread(() -> {

                        closeProgress();
                        textView.setText(message);

                    });

                }

                @Override
                public void onSuccess(String joke) {
                    runOnUiThread(() -> {

                        closeProgress();
                        textView.setText(joke);

                    });
                }
            });

        });

        // 進入下一頁
        Button button3 = findViewById(R.id.button3);

        button3.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);

        });

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