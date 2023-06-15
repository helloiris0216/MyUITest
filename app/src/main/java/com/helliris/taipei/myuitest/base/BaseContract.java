package com.helliris.taipei.myuitest.base;

public interface BaseContract {

    interface onListener<T> {

        void onFail(String message);

        void onSuccess(T t);

        default void onFail(int stringID, Object... message) {

        }

        default void onSuccess() {

        }

    }

}
