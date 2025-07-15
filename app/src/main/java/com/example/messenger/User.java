package com.example.messenger;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User {
    private final String id;
    private final String name;
    private final String lastName;
    private final boolean isOnline;

    public User(String id, String name, String lastName, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.isOnline = isOnline;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
