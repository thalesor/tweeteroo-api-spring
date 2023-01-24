package com.tweeteroo.api.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tweeteroo.api.dto.TweetDTO;
import com.tweeteroo.api.models.Tweet;
import com.tweeteroo.api.models.User;

@RestController
@RequestMapping("api/tweets")
public class TweetsController {

    private List<Tweet> tweetList = new ArrayList<>();

    @GetMapping()
    public ResponseEntity<HashMap<String, Object>> getTweets(@RequestParam int page) {

        HashMap<String, Object> tweetsMap = new HashMap<>();

        tweetsMap.put("content", makeAuxTweetList(tweetList, page));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tweetsMap);

    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<List<Tweet>> getTweetsFromUserName(
            @PathVariable("username") String name) {

        List<Tweet> filteredList;
        List<Tweet> originalList = tweetList;

        filteredList = originalList.stream()
                .filter(tweet -> tweet.getUserName().equals(name))
                .collect(Collectors.toList());

        Collections.reverse(filteredList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filteredList);

    }

    public List<Tweet> makeAuxTweetList(List<Tweet> originalList, int page) {

        final int totalTweets = originalList.size();
        final int perPage = 5;

        List<Tweet> auxList = new ArrayList<>();

        int fromIndex = page * perPage;
        Collections.reverse(originalList);

        if (!originalList.isEmpty()) {
            if (page == 0) {
                if (totalTweets < perPage)
                    auxList = originalList.subList(0, totalTweets);
                else
                    auxList = originalList.subList(0, perPage);

            } else {
                Collections.reverse(originalList);
                int maxPageAllowed = totalTweets / perPage;

                if (page < maxPageAllowed) {
                    auxList = originalList.subList(fromIndex, (fromIndex + perPage));
                } else if (page == maxPageAllowed) {
                    int remaining = totalTweets % page;
                    auxList = originalList.subList(fromIndex, (fromIndex + remaining));
                } else
                    auxList = new ArrayList<>();
            }
        }
        return auxList;
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
