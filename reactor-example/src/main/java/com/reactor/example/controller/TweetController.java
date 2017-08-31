package com.reactor.example.controller;

import com.reactor.example.pojo.Tweet;
import com.reactor.example.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping("/")
    public Tweet save(@RequestBody Tweet tweet) {
        return tweetService.save(tweet);
    }

    @PostMapping("/zip")
    public void createZip(@RequestBody  Tweet tweet){
        tweetService.createZip(tweet);
    }

    @PostMapping("/decompress")
    public Mono<List<List<File>>> decompress(@RequestBody String zipPath){
        return tweetService.writeTweetsFromFlux(zipPath);
    }

}
