package com.tweetapp.dtos.request;

import com.tweetapp.dtos.response.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class RegistrationRequest extends UserDTO {
    @NotBlank
    @Size(min = 8, message = "Password must be 8 character long")
    private String password;
}
