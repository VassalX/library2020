package com.library2020.repository;

import com.library2020.model.Order;
import com.library2020.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
