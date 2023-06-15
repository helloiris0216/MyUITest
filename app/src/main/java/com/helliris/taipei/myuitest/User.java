package com.helliris.taipei.myuitest;

import androidx.annotation.NonNull;

import java.util.Locale;

public class User {

    public String id;
    public String name;
    public Integer level;

    public User(String id, String name, Integer level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "id = %s \nname = %s \nlevel = %d", id, name, level);
    }
}
