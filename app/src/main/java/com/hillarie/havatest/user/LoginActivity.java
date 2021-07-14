package com.hillarie.havatest.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.hillarie.havatest.R;
import com.hillarie.havatest.rides.RidesActivity;
/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public class LoginActivity extends AppCompatActivity implements Contract.LoginView {

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(new LoginRepositoryImpl(), this);

        final EditText emailView = findViewById(R.id.et_phonenumber);
        final EditText passwordView = findViewById(R.id.et_password);
        Button loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();

                LoginCredentials credentials = new LoginCredentials(email, password);
                presenter.login(credentials);
            }

        });
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(this, RidesActivity.class));
        finish();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}