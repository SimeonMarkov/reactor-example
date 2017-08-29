package com.reactor.example.service;

import com.reactor.example.pojo.Tweet;
import com.reactor.example.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }
}
