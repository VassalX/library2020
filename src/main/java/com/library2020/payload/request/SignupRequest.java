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
    @Size(min = 6, max = 254)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 200)
    private String address;

    @NotBlank
    @Size(min = 6, max = 200)
    private String fullName;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private Set<String> role;
}
