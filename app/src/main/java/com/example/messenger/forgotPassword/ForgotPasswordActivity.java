package com.example.messenger.forgotPassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
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
import com.example.messenger.main.MainActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ForgotPasswordViewModel viewModel;
    private Button btnResetPwd;
    private EditText etEmailResetPwd;
    private TextView tvResetPwdInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setUpClickListeners();
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        viewModel.getIsSent().observe(this, sent -> {
           if (sent){
               tvResetPwdInfo.setVisibility(View.VISIBLE);
           } else {
               tvResetPwdInfo.setVisibility(View.GONE);
           }
        });
    }

    private void setUpClickListeners() {
        btnResetPwd.setOnClickListener(view -> {
            sendEmail();
        });
    }

    private void initViews(){
        btnResetPwd = findViewById(R.id.btnRegistration);
        etEmailResetPwd = findViewById(R.id.etEmailResetPwd);
        tvResetPwdInfo = findViewById(R.id.tvResetPwdInfo);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    private void sendEmail(){
        String email = etEmailResetPwd.getText().toString();
        if (email.isEmpty()){
            Toast.makeText(this, R.string.toast_login, Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Введите корректный email", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.sendToEmail(email);
            launchMainScreen();
        }
    }

    private void launchMainScreen() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(MainActivity.newIntent(this));
            finish();
        }, 5000);

    }
}