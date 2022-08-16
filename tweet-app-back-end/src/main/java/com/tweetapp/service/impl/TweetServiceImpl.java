package com.tweetapp.service.impl;

import com.tweetapp.dtos.request.TweetRequest;
import com.tweetapp.dtos.response.ReplyDTO;
import com.tweetapp.dtos.response.TweetDTO;
import com.tweetapp.exception.EmptyResourceContentException;
import com.tweetapp.exception.ResourceNotFoundException;
import com.tweetapp.model.ReplyEntity;
import com.tweetapp.model.TweetEntity;
import com.tweetapp.model.UserEntity;
import com.tweetapp.repository.ReplyRepository;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReplyRepository replyRepository;

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void save(String username, String tweet) throws ResourceNotFoundException {
        UserEntity userEntity = validateUsername(username);
        TweetEntity tweetEntity = new TweetEntity();
        tweetEntity.setTweet(tweet);
        tweetEntity.setUserId(userEntity);

        tweetRepository.save(tweetEntity);

        log.debug("User: {} posted Tweet: {}", username, tweet);
    }

    @Override
    public void update(String username, Integer tweetId, TweetRequest tweet) throws ResourceNotFoundException {
        validateUsername(username);
        Optional<TweetEntity> tweetEntity = tweetRepository.findById(tweetId);

        if (tweetEntity.isEmpty()) {
            throw new ResourceNotFoundException("Tweet Not Found!");
        }

        TweetEntity entity = tweetEntity.get();
        entity.setTweet(tweet.getTweet());

        tweetRepository.save(entity);
        log.debug("User: {} edited tweetId: {}", username, tweetId);
    }

    @Override
    public void delete(String username, String tweetId) throws ResourceNotFoundException {
        UserEntity userEntity = validateUsername(username);
        Optional<TweetEntity> tweetEntity = tweetRepository.findById(Integer.parseInt(tweetId));
        if (tweetEntity.isEmpty()) {
            throw new ResourceNotFoundException("Tweet Not Found!");
        }

        if (tweetEntity.get().getUserId() == userEntity) {
            tweetRepository.delete(tweetEntity.get());
            log.debug("User: {} deleted tweetId: {}", username, tweetId);
        }

    }

    @Override
    public void like(String username, Integer tweetId) throws ResourceNotFoundException {
        Optional<TweetEntity> tweetEntity = tweetRepository.findById(tweetId);

        if (tweetEntity.isEmpty()) {
            throw new ResourceNotFoundException("Tweet Not Found!");
        }

        TweetEntity entity = tweetEntity.get();
        entity.setLikes(entity.getLikes() + 1);

        tweetRepository.save(entity);
        log.debug("User: {} liked tweetId: {}", username, tweetId);
    }

    @Override
    public void reply(String username, Integer tweetId, TweetRequest tweetRequest) throws ResourceNotFoundException {
        UserEntity userEntity = validateUsername(username);
        Optional<TweetEntity> tweetEntity = tweetRepository.findById(tweetId);

        if (tweetEntity.isEmpty()) {
            throw new ResourceNotFoundException("Tweet Not Found!");
        }

        ReplyEntity replyEntity = new ReplyEntity();

        replyEntity.setReply(tweetRequest.getTweet());
        replyEntity.setUserId(userEntity);
        replyEntity.setLikes(0);

        TweetEntity entity = tweetEntity.get();
        List<ReplyEntity> replyEntityList = new ArrayList<>(entity.getReply());
        replyEntityList.add(replyRepository.save(replyEntity));
        entity.setReply(replyEntityList);

        tweetRepository.save(entity);
        log.debug("User: {} replied to tweetId: {}", username, tweetId);
    }

    @Override
    public List<TweetDTO> getUserTweets(String username) throws EmptyResourceContentException {
        List<TweetDTO> list = tweetRepository.findByUserId_UserId(username).stream().map(this::tweetEntityToDTO).collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new EmptyResourceContentException("You haven't posted any tweet yet!");
        }
        return list;
    }

    @Override
    public List<TweetDTO> getAllTweets() throws EmptyResourceContentException {
        List<TweetDTO> list = tweetRepository.findAll().stream().sorted((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt())).map(this::tweetEntityToDTO).collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new EmptyResourceContentException("No Tweets posted");
        }
        return list;
    }

    private UserEntity validateUsername(String username) throws ResourceNotFoundException {
        Optional<UserEntity> optional = userRepository.findByUserId(username);
        if (optional.isPresent()) return optional.get();
        else {
            throw new ResourceNotFoundException("Invalid Username: " + username);
        }
    }

    private TweetDTO tweetEntityToDTO(TweetEntity tweetEntity) {
        List<ReplyDTO> reply = tweetEntity.getReply().stream().sorted((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt())).map(this::replyEntityToDTO).collect(Collectors.toList());

        TweetDTO tweet = new TweetDTO();

        tweet.setTweetId(tweetEntity.getId());
        tweet.setTweet(tweetEntity.getTweet());
        tweet.setLikes(tweetEntity.getLikes());
        tweet.setUserId(tweetEntity.getUserId().getId());
        tweet.setFirstName(tweetEntity.getUserId().getFirstName());
        tweet.setLastName(tweetEntity.getUserId().getLastName());
        tweet.setUsername(tweetEntity.getUserId().getUserId());
        tweet.setAvatar(tweetEntity.getUserId().getAvatar());
        tweet.setTimestamp(tweetEntity.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        tweet.setEdited(!tweetEntity.getCreateAt().equals(tweetEntity.getUpdatedAt()));
        tweet.setReply(reply);

        return tweet;
    }

    private ReplyDTO replyEntityToDTO(ReplyEntity replyEntity) {
        UserEntity userEntity = replyEntity.getUserId();
        ReplyDTO dto = new ReplyDTO();

        dto.setReply(replyEntity.getReply());
        dto.setLikes(replyEntity.getLikes());
        dto.setReplyId(replyEntity.getId());
        dto.setTimestamp(replyEntity.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dto.setEdited(!replyEntity.getCreateAt().equals(replyEntity.getUpdatedAt()));
        dto.setFirstName(userEntity.getFirstName());
        dto.setLastName(userEntity.getLastName());
        dto.setUsername(userEntity.getUserId());
        dto.setAvatar(userEntity.getAvatar());
        dto.setUserId(userEntity.getId());

        return dto;
    }

}
