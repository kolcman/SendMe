package com.example.messenger.forgotPassword;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isSent = new MutableLiveData<>(false);
    private FirebaseAuth auth;
    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> getIsSent() {
        return isSent;
    }

    public void sendToEmail(String email) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener(success ->{
            isSent.setValue(true);
        }).addOnFailureListener( error -> {
           isSent.setValue(false);
            Log.d("SendEmail", error.getMessage());
        });
    }
}
