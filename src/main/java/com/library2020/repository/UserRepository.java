package com.library2020.repository;

import com.library2020.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phone);
    Boolean existsByPhoneNumber(String phone);
}