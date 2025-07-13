package com.example.messenger.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.messenger.R;
import com.example.messenger.User;
import com.example.messenger.forgotPassword.ForgotPasswordActivity;
import com.example.messenger.сhatList.ChatListActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnSign;
    private TextView tvForgotPwd;
    private TextView tvRegistration;

    private MainViewModel viewModel;

    private boolean isFirstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setUpClickListeners();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getIsLogin().observe(this, isLogin -> {
            if (isFirstTime) {
                isFirstTime = false;
                return;
            }

            if (isLogin) {
                launchChatScreen();
            } else {
                Toast.makeText(
                        this,
                        R.string.toast_incorrect_email_and_pwd,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

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
    }

    private void login() {
        String userLogin = etEmail.getText().toString();
        String userPassword = etPassword.getText().toString();
        if (!userLogin.isEmpty() && !userPassword.isEmpty()) {
            viewModel.loginUser(userLogin, userPassword);
        } else {
            Toast.makeText(this, R.string.toast_login, Toast.LENGTH_SHORT).show();
        }
    }
    private void launchChatScreen() {
        startActivity(ChatListActivity.newIntent(this));
    }

    private void launchForgotPwdScreen() {
        startActivity(ForgotPasswordActivity.newIntent(this));
    }

    public static Intent newIntent(Context context){
        return new Intent(context, MainActivity.class);
    }
}