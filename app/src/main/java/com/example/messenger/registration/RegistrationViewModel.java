package com.example.messenger.registration;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationViewModel extends AndroidViewModel {

    private final FirebaseAuth auth;

    private MutableLiveData<String> isError = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();


    public LiveData<String> getIsError() {
        return isError;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }

    public void singUp(String email,
                       String password,
                       String name,
                       String lastName
    ) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(error -> {
                    isError.setValue(error.getMessage());
                });
    }
}
