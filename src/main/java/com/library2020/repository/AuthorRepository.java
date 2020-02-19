package com.library2020.repository;

import com.library2020.model.Author;
import com.library2020.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByFullName(String fullName);
    Boolean existsByFullName(String fullname);
}
