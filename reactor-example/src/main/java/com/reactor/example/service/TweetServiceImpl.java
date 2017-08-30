package com.reactor.example.service;

import com.reactor.example.pojo.Tweet;
import com.reactor.example.repository.TweetRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    private List<String> fillTweetJsonsDirectory(Tweet tweet) {
        File f = new File("src/main/resources/tweets");
        f.mkdir();
        for (int i = 0; i < 100; i++) {
            File tweetJson = new File("src/main/resources/tweets/tweet" + RandomStringUtils.randomAlphanumeric(10) + ".json");
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
        return new ArrayList<String>(Arrays.asList(f.list()));
    }

    @Override
    public void createZip(Tweet tweet) {
        List<String> jsonFilesNames = fillTweetJsonsDirectory(tweet);
        File zipFile = new File("src/main/resources/tweets.zip");
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
                FileOutputStream out = new FileOutputStream(zipFile);
                ZipOutputStream zos = new ZipOutputStream(out);

                for (String json : jsonFilesNames) {
                    ZipEntry entry = new ZipEntry(json);
                    zos.putNextEntry(entry);
                    FileInputStream input = new FileInputStream("src/main/resources/tweets/" + json);
                    byte[] bytes = json.getBytes();
                    int lenght;
                    while ((lenght = input.read(bytes)) > 0) {
                        zos.write(bytes, 0, lenght);
                    }

                    input.close();
                }

                zos.closeEntry();
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
