package com.library2020.repository;

import com.library2020.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Boolean existsByName(String name);
    Optional<Category> findByName(String name);
}
