package com.tweetapp.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.constants.KafkaConstants;
import com.tweetapp.exception.ResourceNotFoundException;
import com.tweetapp.service.TweetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
@Service
@AllArgsConstructor
public class TopicListener {

    private final TweetService tweetService;

    @KafkaListener(topics = KafkaConstants.TOPIC_DELETE_TWEET, groupId = "group_id")
    public void consumeDeleteTweetTopic(ConsumerRecord<String, String> payload) {
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, String> hashMap = null;
        try {
            hashMap = objectMapper.readValue(payload.value(), HashMap.class);
            tweetService.delete(hashMap.get("username"), hashMap.get("tweetId"));

        } catch (JsonProcessingException e) {
            log.error("[INTERNAL_SERVER_ERROR] [Server Error] Timestamp: {}, Delete Operation failed, tweetId: {}, Details: {}", LocalDateTime.now(), hashMap.get("tweetId"), e.getMessage());
        } catch (ResourceNotFoundException e) {
            log.error("[NOT_FOUND] [Record Not Found] Timestamp: {}, Delete Operation failed, tweetId: {}, Details: {}", LocalDateTime.now(), hashMap.get("tweetId"), e.getMessage());
        }

    }

    @KafkaListener(topics = KafkaConstants.TOPIC_ADD_TWEET, groupId = "group_id")
    public void consumeAddTweetTopic(ConsumerRecord<String, String> payload) {
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, String> hashMap = null;
        try {
            hashMap = objectMapper.readValue(payload.value(), HashMap.class);
            tweetService.save(hashMap.get("username"), hashMap.get("tweetRequest"));

        } catch (JsonProcessingException e) {
            log.error("[INTERNAL_SERVER_ERROR] [Server Error] Timestamp: {}, Tweet save Operation failed, Details: {}", LocalDateTime.now(), e.getMessage());
        } catch (ResourceNotFoundException e) {
            log.error("[NOT_FOUND] [Record Not Found] Timestamp: {}, Tweet save Operation failed, Details: {}", LocalDateTime.now(), e.getMessage());
        }

    }
}
