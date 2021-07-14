package com.hillarie.havatest.user;
/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public class LoginRepositoryImpl implements LoginRepository {

    @Override
    public void login(LoginCredentials credentials, LoginListener loginListener) {

        if (credentials.isValid()) {
            loginListener.onSuccess();
        } else {
            loginListener.onFailure("Invalid credentials");
        }

    }

}