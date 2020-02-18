package com.library2020.model;

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
    private Book book;
}
