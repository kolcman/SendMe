package com.example.messenger.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class MainViewModel extends AndroidViewModel {

    private FirebaseAuth auth;
    private MutableLiveData<Boolean> isLogin = new MutableLiveData<>(false);

    public MainViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();

    }

    public LiveData<Boolean> getIsLogin() {
        return isLogin;
    }

    public void loginUser(String email, String pwd) {
        auth.signInWithEmailAndPassword(email, pwd)
                .addOnSuccessListener(authResult -> {
                    isLogin.setValue(true);
                    Log.d("login", isLogin.getValue().toString());
                }).addOnFailureListener(error -> {
                    isLogin.setValue(false);
                    Log.d("login", isLogin.getValue().toString());
                });
    }

    public void logout(){
        auth.signOut();
        Log.d("login", "Logout");
        isLogin.setValue(false);
    }
}
