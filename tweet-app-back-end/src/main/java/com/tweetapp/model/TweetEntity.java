package com.tweetapp.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_tweet")
@Data
public class TweetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Tweet can't be blank")
    private String tweet;

    @OneToOne
    private UserEntity userId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ReplyEntity> reply;

    @Column(columnDefinition = "integer default 0")
    private long likes;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
