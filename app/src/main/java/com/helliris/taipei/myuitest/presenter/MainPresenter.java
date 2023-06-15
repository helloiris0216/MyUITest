package com.helliris.taipei.myuitest.presenter;

import com.helliris.taipei.myuitest.User;
import com.helliris.taipei.myuitest.contract.MainContract;
import com.helliris.taipei.myuitest.model.DataManager;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View iView;


    public MainPresenter(MainContract.View iView) {
        this.iView = iView;
    }

    @Override
    public void requestUserName() {

        final User user = new DataManager().requestUserName();
        iView.receivedUserName(String.format("%s %s", user.id, user.name));

    }

}
