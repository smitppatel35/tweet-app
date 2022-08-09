package com.tweetapp.dtos.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReplyDTO {
    private Integer replyId;
    private String reply;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String username;
    private String avatar;
    private Integer likes;
    private boolean edited;
    private String timestamp;
}
