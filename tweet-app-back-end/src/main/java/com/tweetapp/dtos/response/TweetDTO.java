package com.tweetapp.dtos.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TweetDTO {
    private Integer tweetId;
    private String tweet;
    private Long likes;
    private String timestamp;
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String avatar;
    private boolean edited;
    private List<ReplyDTO> reply;
}
