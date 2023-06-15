package com.helliris.taipei.myuitest.presenter;

import com.helliris.taipei.myuitest.User;
import com.helliris.taipei.myuitest.base.BaseContract;
import com.helliris.taipei.myuitest.contract.ListContract;

import java.util.List;

public class ListPresenter implements ListContract.Presenter {

    private final ListContract.Model iModel;


    public ListPresenter(ListContract.Model iModel) {
        this.iModel = iModel;
    }

    @Override
    public void requestUsers(BaseContract.onListener<List<User>> callback) {

        iModel.getUsers(new BaseContract.onListener<List<User>>() {
            @Override
            public void onFail(String message) {
                callback.onFail(message);
            }

            @Override
            public void onSuccess(List<User> users) {
                callback.onSuccess(users);
            }
        });

    }

}
