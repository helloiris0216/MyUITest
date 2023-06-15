package com.helliris.taipei.myuitest.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.helliris.taipei.myuitest.Constant;
import com.helliris.taipei.myuitest.R;

import java.util.Locale;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Integer level = getIntent().getIntExtra(Constant.TAG_LEVEL, 0);
        String name = getIntent().getStringExtra(Constant.TAG_NAME);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(name);
        }

        TextView textView = findViewById(R.id.textView);
        textView.setText(String.format(Locale.getDefault(), "level is %d", level));

        EditText editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.user_btn);
        button.setOnClickListener(v -> {

            String content = editText.getText().toString();
            textView.setText(String.format(Locale.getDefault(), "%s is level %d", content, level));

        });

    }

}