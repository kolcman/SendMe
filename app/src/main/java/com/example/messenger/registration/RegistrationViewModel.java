package com.example.messenger.registration;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationViewModel  extends AndroidViewModel {

    private final FirebaseAuth auth;

    private MutableLiveData<Boolean> isError = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void registration(String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    isError.setValue(false);
                }).addOnFailureListener( error -> {
                   isError.setValue(true);
                    Log.d("Registration", error.getMessage());
                });
    }
}
