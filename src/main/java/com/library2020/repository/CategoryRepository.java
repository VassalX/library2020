package com.library2020.repository;

import com.library2020.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Boolean existsByName(String name);
}
