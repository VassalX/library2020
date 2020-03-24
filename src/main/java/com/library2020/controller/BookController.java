package com.library2020.controller;

import com.library2020.amazon.AmazonClient;
import com.library2020.model.Author;
import com.library2020.model.Book;
import com.library2020.model.BookInstance;
import com.library2020.model.Category;
import com.library2020.payload.request.BookRequest;
import com.library2020.payload.request.SignupRequest;
import com.library2020.payload.response.MessageResponse;
import com.library2020.repository.AuthorRepository;
import com.library2020.repository.BookInstanceRepository;
import com.library2020.repository.BookRepository;
import com.library2020.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    AmazonClient amazonClient;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookInstanceRepository bookInstanceRepository;

    @GetMapping("/authors")
    public  ResponseEntity<?> getAllAuthors(){
        return  ResponseEntity.ok(authorRepository.findAll());
    }

    @GetMapping("/categories")
    public  ResponseEntity<?> getAllCategories(){
        return  ResponseEntity.ok(categoryRepository.findAll());
    }

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

    @GetMapping(value = "/", params = "author")
    public ResponseEntity<?> getBooksByAuthor(@RequestParam(name = "author") String fullName){
        Optional<Author> author = authorRepository.findByFullName(fullName);
        if(author.isPresent()){
            return ResponseEntity.ok(author.get().getBooks());
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            String.format("Error: Author with name: %s doesn't exist",fullName)));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable(value = "id") Long id,
                                            @Valid @RequestPart(value = "request") BookRequest bookRequest,
                                            @RequestPart(value = "picture") MultipartFile picture) throws IOException {
        Optional<Book> foundBook = bookRepository.findById(id);
        if(!foundBook.isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            String.format("Error: Book with id: %d doesn't exist",id)));
        }
        Book book = foundBook.get();
        book.setName(bookRequest.getName());
        book.setNumberOfPages(bookRequest.getNumberOfPages());
        book.setPrice(bookRequest.getPrice());
        book.setPublishingHouse(bookRequest.getPublishingHouse());
        book.setPublishingYear(bookRequest.getPublishingYear());
        book.setCity(bookRequest.getCity());
        book.setDescription(bookRequest.getDescription());

        if(bookRequest.getAuthors() != null){
            for (Author oldAuthor : book.getAuthors()) {
                if (!bookRequest.getAuthors().contains(oldAuthor.getFullName())) {
                    book.removeAuthor(oldAuthor);
                }
            }
            bookRequest.getAuthors().forEach(author -> {
                Optional<Author> found = authorRepository.findByFullName(author);
                book.addAuthor(found.orElse(new Author(author)));
            });
        }

        if(bookRequest.getCategory() != null){
            Optional<Category> found = categoryRepository.findByName(bookRequest.getCategory());
            if (!found.isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(
                                String.format("Error: Category %s doesn't exist",
                                        bookRequest.getCategory())));
            } else {
                book.setCategory(found.get());
            }
        }

        if(book.getNumberOfInstances() > bookRequest.getNumberOfInstances()){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            String.format("Error: new number of instances (%d) cannot be less than %d",
                                    bookRequest.getNumberOfInstances(),
                                    book.getNumberOfInstances())));
        }

        for(long i = book.getNumberOfInstances(); i < bookRequest.getNumberOfInstances(); i++){
            book.addInstance(new BookInstance(true));
        }

        if(picture != null){
            if(book.getPicture() != null){
                amazonClient.deleteFileFromS3Bucket(book.getPicture());
            }
            String fileUrl = amazonClient.uploadFile(picture);
            book.setPicture(fileUrl);
        }

        bookRepository.save(book);

        return ResponseEntity.ok(book);
    }

    @PostMapping("/")
    public ResponseEntity<?> createBook(@Valid @RequestPart(value = "request") BookRequest bookRequest,
                                        @RequestPart(value = "picture") MultipartFile picture) throws IOException {
        Book newBook = new Book(
                bookRequest.getIsbn(),
                bookRequest.getName(),
                bookRequest.getNumberOfPages(),
                bookRequest.getPrice(),
                bookRequest.getPublishingHouse(),
                bookRequest.getPublishingYear(),
                bookRequest.getCity(),
                bookRequest.getDescription()
        );

        if(bookRequest.getAuthors() != null){
            bookRequest.getAuthors().forEach(author -> {
                Optional<Author> found = authorRepository.findByFullName(author);
                newBook.addAuthor(found.orElse(new Author(author)));
            });
        }

        if(bookRequest.getCategory() != null){
            Optional<Category> found = categoryRepository.findByName(bookRequest.getCategory());
            if(found.isPresent()){
                newBook.setCategory(found.get());
            }else{
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(
                                String.format("Error: Category %s doesn't exist",
                                        bookRequest.getCategory())));
            }
        }

        for(int i = 0; i < bookRequest.getNumberOfInstances(); i++){
            newBook.addInstance(new BookInstance(true));
        }

        if(picture != null){
            String fileUrl = amazonClient.uploadFile(picture);
            newBook.setPicture(fileUrl);
        }

        bookRepository.save(newBook);

        return ResponseEntity.ok(new MessageResponse("Book was created successfully!"));
    }
}
