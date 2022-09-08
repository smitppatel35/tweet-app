package com.tweetapp.dtos;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String name;
    private String gender;
    private String email;
}
