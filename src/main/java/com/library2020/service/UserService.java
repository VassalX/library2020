package com.library2020.service;

import com.library2020.model.User;

public interface UserService {
    void save(User user);

    User findByPhoneNumber(String phone);
}