package com.helliris.taipei.myuitest.model;

import com.helliris.taipei.myuitest.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataManager {

    public List<User> getUserList() {

        List<User> users = new ArrayList<>();

        Random random = new Random();

        for (int i = 1; i < 11; i++) {

            User user = new User("00" + i, "User00" + i, 50 + i);
            users.add(user);

        }

        return users;
    }

    public User requestUserName() {

        return new User("001", "Iris Yen", 99);
    }

}
