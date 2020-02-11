package com.library2020.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@RequiredArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "phoneNumber"
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter @Setter @NonNull
    private String fullName;

    @Getter @Setter @NonNull
    private String phoneNumber;

    @Getter @Setter @NonNull
    private LocalDate dateOfBirth;

    @Getter @Setter @NonNull
    private String address;

    @Getter @Setter @NonNull
    private String password;

    @Transient
    @Getter @NonNull
    private String passwordConfirm;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    @Getter @Setter @NonNull
    private Set<Role> roles;
}
