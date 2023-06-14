package com.helliris.taipei.myuitest;

import androidx.test.espresso.idling.CountingIdlingResource;

public class Idling {

    private static CountingIdlingResource _idlingResource;

    public static CountingIdlingResource getResource() {

        if (_idlingResource == null) {
            _idlingResource = new CountingIdlingResource("test");
        }

        return _idlingResource;
    }

}
