package com.tweetapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.constants.KafkaConstants;
import com.tweetapp.dtos.SuccessResponse;
import com.tweetapp.dtos.request.TweetRequest;
import com.tweetapp.dtos.response.TweetDTO;
import com.tweetapp.exception.EmptyResourceContentException;
import com.tweetapp.exception.ResourceNotFoundException;
import com.tweetapp.service.TweetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/tweets")
@Tag(name = "Tweet", description = "Manage Tweets")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TweetController {

    private final TweetService tweetService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @PostMapping("/{username}/add")
    public ResponseEntity<SuccessResponse> addTweet(
            @PathVariable("username") String username,
            @Valid @RequestBody TweetRequest tweetRequest) throws JsonProcessingException {

        Map<String, Object> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("tweetRequest", tweetRequest.getTweet());

        kafkaTemplate.send(KafkaConstants.TOPIC_ADD_TWEET, objectMapper.writeValueAsString(payload));
        return ResponseEntity.ok(new SuccessResponse("Tweet Published"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TweetDTO>> getAllTweets() throws EmptyResourceContentException {
        return ResponseEntity.ok(tweetService.getAllTweets());
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getAllTweetsOfUser(@PathVariable("username") String username) throws EmptyResourceContentException {
        List<TweetDTO> userTweets = tweetService.getUserTweets(username);
        return ResponseEntity.ok(userTweets);
    }

    @PutMapping("/{username}/like/{id}")
    public ResponseEntity<SuccessResponse> like(
            @PathVariable("username") String username,
            @PathVariable("id") int id) throws ResourceNotFoundException {
        tweetService.like(username, id);
        return ResponseEntity.ok(new SuccessResponse("Tweet liked"));
    }

    @PostMapping("/{username}/reply/{id}")
    public ResponseEntity<SuccessResponse> reply(
            @PathVariable("username") String username,
            @PathVariable("id") int id,
            @RequestBody TweetRequest tweetRequest) throws ResourceNotFoundException {
        tweetService.reply(username, id, tweetRequest);
        return ResponseEntity.ok(new SuccessResponse("Tweet Reply Published"));
    }

    @PutMapping("/{username}/update/{id}")
    public ResponseEntity<SuccessResponse> update(
            @PathVariable("username") String username,
            @PathVariable("id") int id,
            @RequestBody TweetRequest tweetRequest) throws ResourceNotFoundException {
        tweetService.update(username, id, tweetRequest);
        return ResponseEntity.ok(new SuccessResponse("Tweet updated"));
    }

    @DeleteMapping("/{username}/delete/{id}")
    public ResponseEntity<String> delete(
            @PathVariable("username") String username,
            @PathVariable("id") String id) throws JsonProcessingException {

        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("tweetId", id);

        kafkaTemplate.send(KafkaConstants.TOPIC_DELETE_TWEET, objectMapper.writeValueAsString(payload));
        return ResponseEntity.ok("Tweet Deleted");
    }
}
