package com.reactor.example.service;

import com.reactor.example.pojo.Tweet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface TweetService {
    Tweet save(Tweet tweet);

    void createZip(Tweet tweet);

    List<File> decompress(String zipPath);

    void writeTweetsFromFlux(String zipPath) throws FileNotFoundException;

    Tweet convertToTweet(String filePath) throws FileNotFoundException;
}
