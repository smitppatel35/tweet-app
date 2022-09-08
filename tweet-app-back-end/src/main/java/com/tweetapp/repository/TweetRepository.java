package com.tweetapp.repository;

import com.tweetapp.model.TweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, Integer> {

    List<TweetEntity> findByUserId_UserId(String userId);
    Optional<TweetEntity> findByIdAndUserId_UserId(Integer tweetId, String userId);
}
