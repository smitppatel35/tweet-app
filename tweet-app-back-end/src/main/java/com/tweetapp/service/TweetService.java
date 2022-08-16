package com.tweetapp.service;

import com.tweetapp.dtos.request.TweetRequest;
import com.tweetapp.dtos.response.TweetDTO;
import com.tweetapp.exception.EmptyResourceContentException;
import com.tweetapp.exception.ResourceNotFoundException;

import java.util.List;

public interface TweetService {
    // Add
    void save(String username, String tweet) throws ResourceNotFoundException;

    // Update tweet
    void update(String username, Integer tweetId, TweetRequest tweet) throws ResourceNotFoundException;

    // delete tweet
    void delete(String username, String tweetId) throws ResourceNotFoundException;

    // like tweet
    void like(String username, Integer tweetId) throws ResourceNotFoundException;

    // reply tweet
    void reply(String username, Integer tweetId, TweetRequest tweetRequest) throws ResourceNotFoundException;

    // get User's tweet
    List<TweetDTO> getUserTweets(String username) throws EmptyResourceContentException;

    // get all tweets
    List<TweetDTO> getAllTweets() throws EmptyResourceContentException;

}
