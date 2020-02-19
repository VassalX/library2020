package com.library2020.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book_instances")
@Getter @Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class BookInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Boolean isPresent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    @JsonBackReference
    private Book book;
}
