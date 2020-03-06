package com.library2020.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderStatus status;

    @NonNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expectedReturnDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date actualReturnDate;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookInstanceId")
    @JsonBackReference
    private BookInstance bookInstance;
}
