package com.reactor.example.service;

import com.reactor.example.pojo.Tweet;
import com.reactor.example.repository.TweetRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet createTweetJson(Tweet tweet)  {
        for(int i = 0; i < 100; i++) {
            File tweetJson = new File("src/main/resources/tweet" + RandomStringUtils.randomAlphanumeric(10) + ".json");
            if (!tweetJson.exists()) {
                try {
                    tweetJson.createNewFile();
                    FileOutputStream out = new FileOutputStream(tweetJson);
                    byte[] bytes = tweet.toString().getBytes();
                    out.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return tweet;
    }
}
