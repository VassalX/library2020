package com.library2020.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
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

    @Transient
    private Long numberOfInstances;

    @NonNull
    @NotBlank
    private String publishingHouse;

    @NonNull
    private Integer publishingYear;

    @NonNull
    @NotBlank
    private String city;

    @NonNull
    @Min(1)
    private Long numberOfPages;

    @NonNull
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToMany
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "authorId"))
    private List<Author> authors;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book_instances")
    private List<BookInstance> bookInstances;

    @PostLoad
    private void onLoad() {
        this.numberOfInstances = bookInstances.stream().filter(BookInstance::getIsPresent).count();
    }
}
