package com.reactor.example.controller;

import com.reactor.example.pojo.Tweet;
import com.reactor.example.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping("/")
    public Tweet save(@RequestBody Tweet tweet) {
        return tweetService.save(tweet);
    }

    @PostMapping("/write")
    public Tweet writeToJson(@RequestBody Tweet tweet) {
        return tweetService.createTweetJson(tweet);
    }

}
