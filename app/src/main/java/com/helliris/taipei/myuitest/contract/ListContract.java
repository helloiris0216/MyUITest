package com.helliris.taipei.myuitest.contract;

import com.helliris.taipei.myuitest.User;
import com.helliris.taipei.myuitest.base.BaseContract;

import java.util.List;

public interface ListContract {

    interface Model {

        void getUsers(BaseContract.onListener<List<User>> callback);

    }

    interface View {

        void receivedUsers(List<User> users);

    }

    interface Presenter {

        void requestUsers(BaseContract.onListener<List<User>> callback);

    }

}
