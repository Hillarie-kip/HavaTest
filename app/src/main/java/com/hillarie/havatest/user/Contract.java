package com.hillarie.havatest.user;
/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public interface Contract {

    interface LoginView {

        void onSuccess();

        void onFailed(String message);

    }

    interface Presenter {

        void login(LoginCredentials loginCredentials);

    }

}