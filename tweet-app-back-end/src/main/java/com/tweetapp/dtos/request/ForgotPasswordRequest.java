package com.tweetapp.dtos.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ForgotPasswordRequest {

//    @NotBlank
//    @Size(min = 8, message = "Current Password must be 8 character long")
//    private String currentPassword;

    @NotBlank
    @Size(min = 8, message = "New Password must be 8 character long")
    private String newPassword;
}
