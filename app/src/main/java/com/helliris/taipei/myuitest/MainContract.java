package com.helliris.taipei.myuitest;

public interface MainContract {

    interface Model {

        void request1(DemoCallback callback);

        void getJoke(DemoCallback callback);

        interface DemoCallback {

            void onReceive(String response);

        }

    }

    interface View {

        void receivedUserName(String name);

    }

    interface Presenter {

        void requestUserName();

    }

}
