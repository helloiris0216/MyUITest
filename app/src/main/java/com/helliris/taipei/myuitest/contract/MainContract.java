package com.helliris.taipei.myuitest.contract;

import com.helliris.taipei.myuitest.base.BaseContract;

public interface MainContract {

    interface Model {

        void setBaseURL(String baseURL);

        void request1(DemoCallback callback);

        void getJoke(BaseContract.onListener<String> callback);

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
