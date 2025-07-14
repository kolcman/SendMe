package com.example.messenger.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.messenger.R;
import com.example.messenger.login.LoginActivity;
import com.example.messenger.сhatList.ChatListActivity;

public class RegistrationActivity extends AppCompatActivity {

    private RegistrationViewModel viewModel;

    private EditText etEmailRegistration;
    private EditText etPasswordRegistration;
    private EditText etNameRegistration;
    private EditText etLastnameRegistration;
    private CheckBox cbAcceptRules;
    private Button btnRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setUpClickListeners();
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        observeViewModels();
    }

    private void observeViewModels() {
        viewModel.getIsError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this,
                        error,
                        Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getUser().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                startActivity(ChatListActivity.newIntent(this));
                finish();
            }
        });
    }
    private void registration() {
        String email = getTrimmedValue(etEmailRegistration);
        String password = getTrimmedValue(etPasswordRegistration);
        String name = getTrimmedValue(etNameRegistration);
        String lastName = getTrimmedValue(etLastnameRegistration);

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this,
                    R.string.toast_empty_fields,
                    Toast.LENGTH_SHORT)
                    .show();
           return;
        } else {
            viewModel.singUp(email, password, name, lastName);
        }
    }
    private void setUpClickListeners() {
        btnRegistration.setOnClickListener(view -> {
            registration();
        });
    }
    private void initViews() {
        etEmailRegistration = findViewById(R.id.etEmailRegistration);
        etPasswordRegistration = findViewById(R.id.etPasswordRegistration);
        etNameRegistration = findViewById(R.id.etNameRegistration);
        etLastnameRegistration = findViewById(R.id.etLastnameRegistration);
        cbAcceptRules = findViewById(R.id.cbAcceptRules);
        btnRegistration = findViewById(R.id.btnRegistration);
    }
    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }
    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }
}