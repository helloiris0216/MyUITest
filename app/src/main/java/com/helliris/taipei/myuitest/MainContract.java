package com.helliris.taipei.myuitest;

public interface MainContract {

    interface Model {

        void request(DemoCallback callback);

        interface DemoCallback {

            void onSuccess(String response);
            void onFail(String err);

        }

    }

    interface View {

        void receivedUserName(String name);

    }

    interface Presenter {

        void requestUserName();

    }

}
