package com.helliris.taipei.myuitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);

        });

    }


    @Override
    public void receivedUserName(String name) {
        textView.setText(name);
    }

}