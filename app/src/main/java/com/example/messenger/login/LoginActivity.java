package com.example.messenger.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.messenger.R;
import com.example.messenger.forgotPassword.ForgotPasswordActivity;
import com.example.messenger.registration.RegistrationActivity;
import com.example.messenger.сhatList.ChatListActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnSign;
    private TextView tvForgotPwd;
    private TextView tvRegistration;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModels();
        setUpClickListeners();


    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSign = findViewById(R.id.btnSign);
        tvForgotPwd = findViewById(R.id.tvForgotPwd);
        tvRegistration = findViewById(R.id.tvRegistration);
    }

    private void setUpClickListeners() {
        btnSign.setOnClickListener(view -> {
            login();
        });
        tvForgotPwd.setOnClickListener(view -> {
            launchForgotPwdScreen();
        });
        tvRegistration.setOnClickListener(view -> {
            launchRegistrationScreen();
        });
    }

    private void observeViewModels() {
        viewModel.getIsError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(
                        this,
                        error,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        viewModel.getUser().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                launchChatScreen();
                finish();
            }
        });
    }

    private void login() {
        String userLogin = etEmail.getText().toString();
        String userPassword = etPassword.getText().toString();
        if (!userLogin.isEmpty() && !userPassword.isEmpty()) {
            viewModel.loginUser(userLogin, userPassword);
        } else {
            Toast.makeText(this, R.string.toast_empty_fields, Toast.LENGTH_SHORT).show();
        }
    }

    private void launchChatScreen() {
        startActivity(ChatListActivity.newIntent(this));
    }

    private void launchForgotPwdScreen() {
        String email = etEmail.getText().toString().trim();
        startActivity(ForgotPasswordActivity.newIntent(this, email));
    }

    private void launchRegistrationScreen() {
        startActivity(RegistrationActivity.newIntent(this));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}