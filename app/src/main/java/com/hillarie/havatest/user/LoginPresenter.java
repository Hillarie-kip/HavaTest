package com.hillarie.havatest.user;

/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */

public class LoginPresenter implements Contract.Presenter {

    private Contract.LoginView loginView;

    private LoginRepository loginRepository;

    public LoginPresenter(LoginRepository loginRepository, Contract.LoginView loginView) {
        this.loginView = loginView;
        this.loginRepository = loginRepository;
    }

    @Override
    public void login(LoginCredentials loginCredentials) {
        loginRepository.login(loginCredentials, new LoginRepository.LoginListener() {

            @Override
            public void onSuccess() {
                loginView.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                loginView.onFailed(message);
            }

        });
    }
}