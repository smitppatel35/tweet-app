package com.tweetapp.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TweetRequest {

    @NotBlank(message = "Tweet can't be blank")
    @Size(max = 144, message = "Tweet should not go beyond 144 characters")
    private String tweet;

}
