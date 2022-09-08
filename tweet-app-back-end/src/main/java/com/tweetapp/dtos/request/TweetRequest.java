package com.tweetapp.dtos.request;

import com.tweetapp.dtos.UserRequest;
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
public class TweetRequest extends UserRequest {

    @NotBlank(message = "Tweet can't be blank")
    @Size(max = 144, message = "Tweet should not go beyond 144 characters")
    private String tweet;

}
