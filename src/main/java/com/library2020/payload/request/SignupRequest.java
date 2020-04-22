package com.library2020.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter @Setter
public class SignupRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String fullName;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    @NotBlank
    @Size(min = 6)
    private String password;

    private Set<String> role;
}
