package com.library2020.controller;

import com.library2020.model.Author;
import com.library2020.model.Book;
import com.library2020.model.BookInstance;
import com.library2020.payload.request.BookRequest;
import com.library2020.payload.request.SignupRequest;
import com.library2020.payload.response.MessageResponse;
import com.library2020.repository.AuthorRepository;
import com.library2020.repository.BookRepository;
import com.library2020.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllBooks(){
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(
            @PathVariable(value = "id") Long id){
        if(bookRepository.existsById(id)){
            return ResponseEntity.ok(bookRepository.findById(id));
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            String.format("Error: Book with id: %d doesn't exist",id)));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createBook(@Valid @RequestBody BookRequest bookRequest){
        if(bookRequest.getAuthors() != null){
            for(int i = 0; i < bookRequest.getAuthors().size(); i++){
                Author author = bookRequest.getAuthors().get(i);
                Optional<Author> found = authorRepository.findByFullName(author.getFullName());
                bookRequest.getAuthors().set(i,found.orElse(author));
            }
        }

        if(bookRequest.getCategory() != null
                && !categoryRepository.existsByName(bookRequest.getCategory().getName())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            String.format("Error: Category %s doesn't exist",
                                    bookRequest.getCategory().getName())));
        }

        Book newBook = new Book(
                bookRequest.getPublishingHouse(),
                bookRequest.getPublishingYear(),
                bookRequest.getCity(),
                bookRequest.getNumberOfPages(),
                bookRequest.getPrice()
        );
        newBook.setAuthors(bookRequest.getAuthors());
        newBook.setCategory(bookRequest.getCategory());

        List<BookInstance> bookInstances = new ArrayList<>();
        for(int i = 0; i < bookRequest.getNumberOfInstances(); i++){
            bookInstances.add(new BookInstance(true));
        }
        newBook.setBookInstances(bookInstances);

        bookRepository.save(newBook);

        return ResponseEntity.ok(new MessageResponse("Book was created successfully!"));
    }
}
