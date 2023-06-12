package com.helliris.taipei.myuitest;

public interface MainContract {

    interface Model {

    }

    interface View {

        void receivedUserName(String name);

    }

    interface Presenter {

        void requestUserName();

    }

}
