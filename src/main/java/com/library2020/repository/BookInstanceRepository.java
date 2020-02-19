package com.library2020.repository;

import com.library2020.model.BookInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {

}
