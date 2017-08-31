package com.reactor.example.controller;

import com.reactor.example.pojo.Tweet;
import com.reactor.example.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

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
    public void decompress(@RequestBody String zipPath) throws FileNotFoundException{
        tweetService.writeTweetsFromFlux(zipPath);
    }

    @PostMapping("/convert")
    public Tweet convert(@RequestBody String filePath) throws FileNotFoundException {
        return tweetService.convertToTweet(filePath);
    }

}
