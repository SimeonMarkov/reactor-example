package com.reactor.example.service;

import com.reactor.example.pojo.Tweet;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.List;

public interface TweetService {
    Tweet save(Tweet tweet);

    void createZip(Tweet tweet);

    List<File> decompress(String zipPath);

    Mono<List<List<File>>> writeTweetsFromFlux(String zipPath);
}
