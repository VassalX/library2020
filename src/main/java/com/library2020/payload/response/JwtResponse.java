package com.library2020.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class JwtResponse {
    private static final String type = "Bearer";
    private String accessToken;
    private Long id;
    private String email;
    private String fullName;
    private String address;
    private Date dateOfBirth;
    private List<String> roles;
}
