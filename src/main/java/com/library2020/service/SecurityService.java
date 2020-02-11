package com.library2020.service;

public interface SecurityService {
    String findLoggedInPhoneNumber();

    void autoLogin(String phone, String password);
}