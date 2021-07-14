package com.hillarie.havatest.user;
/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public interface LoginRepository {

    interface LoginListener {

        void onSuccess();

        void onFailure(String message);

    }

    void login(LoginCredentials credentials, LoginListener loginListener);

}