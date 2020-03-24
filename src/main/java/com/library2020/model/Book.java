package com.library2020.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter @Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NonNull
    @NotBlank
    private String isbn;

    private String picture;

    @NonNull
    @NotBlank
    private String name;

    @NonNull
    @Min(1)
    private Long numberOfPages;

    @NonNull
    private BigDecimal price;

    @Transient
    private Long numberOfInstances = 0L;

    @NonNull
    @NotBlank
    private String publishingHouse;

    @NonNull
    private Integer publishingYear;

    @NonNull
    @NotBlank
    private String city;

    @NonNull
    @NotBlank
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId")
    @JsonManagedReference
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "authorId"))
    @JsonManagedReference
    private Set<Author> authors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "book")
    @JsonManagedReference
    private List<BookInstance> bookInstances = new ArrayList<>();

    @PostLoad
    private void onLoad() {
        this.numberOfInstances = (long) bookInstances.size();
    }

    public void addInstance(BookInstance bookInstance){
        bookInstances.add(bookInstance);
        bookInstance.setBook(this);
    }

    public void addAuthor(Author author){
        authors.add(author);
        author.getBooks().add(this);
    }

    public void setCategory(Category category){
        this.category = category;
        category.getBooks().add(this);
    }

    public void removeAuthor(Author author){
        authors.remove(author);
        author.getBooks().remove(this);
    }
}
