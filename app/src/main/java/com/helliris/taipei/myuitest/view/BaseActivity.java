package com.helliris.taipei.myuitest.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.helliris.taipei.myuitest.R;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void showProgress(final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    progressDialog = new ProgressDialog(BaseActivity.this);
                    progressDialog.setMessage(message);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void closeProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    progressDialog = null;
                }
            }
        });
    }

}