package com.helliris.taipei.myuitest;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View iView;


    public MainPresenter(MainContract.View iView) {
        this.iView = iView;
    }

    @Override
    public void requestUserName() {

//        final User user = new DataManager().requestUserName();
//        iView.receivedUserName(String.format("%s %s", user.id, user.name));

    }

}
