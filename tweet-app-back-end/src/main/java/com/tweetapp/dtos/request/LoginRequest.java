package com.tweetapp.dtos.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString
public class LoginRequest {

    @Email(message = "Email is not valid")
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be 8 character long")
    private String password;
}
