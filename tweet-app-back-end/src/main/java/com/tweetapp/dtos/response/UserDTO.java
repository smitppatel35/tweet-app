package com.tweetapp.dtos.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString
@RequiredArgsConstructor
public class UserDTO {

    @NotBlank(message = "First Name is required")
    private String firstName;
    private String lastName;

    @Email(message = "Email is not valid")
    @NotBlank
    private String email;

    private String username;

    @Size(min = 10, max = 10, message = "Contact Number should be 10 char long.")
    private String contactNumber;
    private String gender;
    private String avatar;
}
