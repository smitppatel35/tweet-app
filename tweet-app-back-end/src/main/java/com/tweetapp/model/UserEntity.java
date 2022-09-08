package com.tweetapp.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_user")
@Data
public class UserEntity {

    // sub
    @Id
    private String id;

    //email
    @Column(nullable = false, unique = true)
    private String email;

    // email -> @username
    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String name;
    private String gender;
    private String avatar;


    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
