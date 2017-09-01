package com.reactor.example.service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.reactor.example.exception.ZipNotFoundException;
import com.reactor.example.pojo.Tweet;
import com.reactor.example.repository.TweetRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.apache.catalina.startup.ExpandWar.deleteDir;

@Component
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    private Logger logger = LoggerFactory.getLogger(TweetServiceImpl.class);

    @Override
    public Tweet save(Tweet tweet) {
        return null;
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
                    int prefix = new Random().nextInt(101);
                    tweet.getUser().setName(tweet.getUser().getName() + prefix);
                    byte[] bytes = tweet.toString().getBytes();
                    out.write(bytes);
                    tweet.getUser().setName(tweet.getUser().getName().replace(String.valueOf(prefix), ""));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>(Arrays.asList(f.list()));
    }

    private boolean deleteDirectory(File dirPath) {
        if (dirPath.isDirectory()) {
            String[] children = dirPath.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dirPath, children[i]));

                if (!success) {
                    return false;
                }
            }
        }

        System.out.println("The directory is deleted.");
        return dirPath.delete();
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

    @Override
    public List<File> decompress(String zipPath){
        List<File> list = new ArrayList<>();
        File unzippedJsonsFolder = new File("src/main/resources/unzipped/");
        unzippedJsonsFolder.mkdir();
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(unzippedJsonsFolder.getPath() + File.separator + fileName);
                list.add(newFile);
                FileOutputStream fos = new FileOutputStream(newFile);
                byte[] bytes = fileName.getBytes();
                int len;
                while ((len = zis.read(bytes)) > 0) {
                    fos.write(bytes, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");
        } catch (IOException e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Tweet convertToTweet(String filePath) {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(reader, Tweet.class);
    }

    @Override
    public void writeTweetsFromFlux(String zipPath) throws FileNotFoundException {
        long start = System.currentTimeMillis();
        File zip = new File(zipPath);
        if(!zip.exists()) {
            throw new ZipNotFoundException("Zip not found!");
        }
        Flux.just(zip.getPath())
                .map(this::decompress).parallel().subscribe(files -> files.forEach(file -> { tweetRepository.save(convertToTweet(file.getPath()));
                }), throwable -> logger.info(throwable.getCause().toString() + " IS THE THROWN EXCEPTION!!!"));
        System.out.println(System.currentTimeMillis() - start + " ms");
    }
}
