package com.example.messenger.forgotPassword;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> success = new MutableLiveData<>(false);
    private MutableLiveData<String> error = new MutableLiveData<>();
    private FirebaseAuth auth;
    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> isSuccess() {
        return success;
    }

    public void sendToEmail(String email) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener(unused ->{
            success.setValue(true);
        }).addOnFailureListener( exception -> {
           success.setValue(false);
           error.setValue(exception.getMessage());
        });
    }
}
