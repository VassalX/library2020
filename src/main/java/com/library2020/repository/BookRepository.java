package com.library2020.repository;

import com.library2020.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    List<Book> findAll();
    Optional<Book> findByIsbn(String isbn);
    Boolean existsByIsbn(String isbn);
}
