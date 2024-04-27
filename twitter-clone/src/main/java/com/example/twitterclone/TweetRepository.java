package com.example.twitterclone;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TweetRepository {
    private static final List<Tweet> tweets = new ArrayList<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    @PostConstruct
    public void initData() {
        // Initialize with some sample tweets
        Tweet tweet1 = new Tweet();
        tweet1.setId(idGenerator.getAndIncrement());
        tweet1.setText("Hello, Twitter!");
        tweet1.setAuthor("user1");
        tweet1.setTimestamp(LocalDateTime.now());
        tweets.add(tweet1);

        Tweet tweet2 = new Tweet();
        tweet2.setId(idGenerator.getAndIncrement());
        tweet2.setText("Spring Boot is awesome!");
        tweet2.setAuthor("user2");
        tweet2.setTimestamp(LocalDateTime.now());
        tweets.add(tweet2);
    }

    public List<Tweet> getAllTweets() {
        return tweets;
    }

    public List<Tweet> getTweetsByUser(String username) {
        List<Tweet> userTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().equals(username)) {
                userTweets.add(tweet);
            }
        }
        return userTweets;
    }

    public Tweet getTweetById(int id) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                return tweet;
            }
        }
        return null;
    }

    public void addTweet(Tweet tweet) {
        tweet.setId(idGenerator.getAndIncrement());
        tweet.setTimestamp(LocalDateTime.now());
        tweets.add(tweet);
    }

    public boolean deleteTweet(int id) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                tweets.remove(tweet);
                return true;
            }
        }
        return false;
    }
}
