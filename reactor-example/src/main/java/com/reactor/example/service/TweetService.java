package com.reactor.example.service;

import com.reactor.example.pojo.Tweet;

public interface TweetService {
    Tweet save(Tweet tweet);

    Tweet createTweetJson(Tweet tweet);
}
