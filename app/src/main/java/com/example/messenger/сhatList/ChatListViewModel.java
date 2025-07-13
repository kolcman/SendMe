package com.example.messenger.сhatList;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class ChatListViewModel extends AndroidViewModel {

    private FirebaseAuth auth;

    public ChatListViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void logout() {
        auth.signOut();
        Log.d("login", "Logout");
    }
}
