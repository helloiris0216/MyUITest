package com.helliris.taipei.myuitest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.helliris.taipei.myuitest.ListAdapter;
import com.helliris.taipei.myuitest.R;
import com.helliris.taipei.myuitest.User;
import com.helliris.taipei.myuitest.base.BaseContract;
import com.helliris.taipei.myuitest.contract.ListContract;
import com.helliris.taipei.myuitest.model.ListModel;
import com.helliris.taipei.myuitest.presenter.ListPresenter;

import java.util.List;

public class ListActivity extends BaseActivity {

    private ListPresenter iPresenter;
    private ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListContract.Model model = new ListModel();
        iPresenter = new ListPresenter(model);

        init();
        getUsers();

    }

    private void init() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ListAdapter();
        recyclerView.setAdapter(adapter);

    }

    private void getUsers() {

        showProgress("載入中...");

        iPresenter.requestUsers(new BaseContract.onListener<List<User>>() {
            @Override
            public void onFail(String message) {

                runOnUiThread(() -> {

                    closeProgress();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);

                    builder.setTitle("Error")
                            .setMessage(message)
                            .setCancelable(false)
                            .create()
                            .show();

                });

            }

            @Override
            public void onSuccess(List<User> users) {

                runOnUiThread(() -> {

                    closeProgress();

                    adapter.setData(users);
                    adapter.notifyDataSetChanged();

                });

            }
        });

    }

}