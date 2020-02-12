package com.library2020.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class SigninRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
