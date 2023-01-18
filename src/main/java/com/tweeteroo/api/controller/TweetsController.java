package com.tweeteroo.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweeteroo.api.dto.TweetDTO;
import com.tweeteroo.api.models.Tweet;
import com.tweeteroo.api.models.User;

@RestController
@RequestMapping("api/tweets")
public class TweetsController {

    private List<Tweet> tweetList = new ArrayList<>();

    @GetMapping()
    public ResponseEntity<HashMap<String, Object>> getTweets() {

        HashMap<String, Object> tweetsMap = new HashMap<String, Object>();
        tweetsMap.put("content", tweetList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tweetsMap);

    }

    @PostMapping()
    public ResponseEntity<String> createTweet(@RequestBody TweetDTO req, @RequestHeader("User") String username) {

        User signedUser = AuthController.signedUser;
        tweetList.add(new Tweet(signedUser.getUserName(),
                signedUser.getAvatar(),
                req.text()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("OK");

    }

}
