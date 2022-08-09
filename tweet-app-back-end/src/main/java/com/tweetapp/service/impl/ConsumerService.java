package com.tweetapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @KafkaListener(topics = "tweet-topic", groupId = "group_id")
    public void consume(Object payload) {
        log.debug("[Consumer] Payload: {}", payload);
    }
}
