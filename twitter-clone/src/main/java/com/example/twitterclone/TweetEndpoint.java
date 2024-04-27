package com.example.twitterclone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.twitter.schema.GetUserTweetsRequest;
import com.example.twitter.schema.GetUserTweetsResponse;
import com.example.twitter.schema.PostTweetRequest;
import com.example.twitter.schema.PostTweetResponse;
import com.example.twitter.schema.DeleteTweetRequest;
import com.example.twitter.schema.DeleteTweetResponse;

@Endpoint
public class TweetEndpoint {
    private static final String NAMESPACE_URI = "http://twitter.com/schema";

    private TweetRepository tweetRepository;

    @Autowired
    public TweetEndpoint(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserTweetsRequest")
    @ResponsePayload
    public GetUserTweetsResponse getUserTweets(@RequestPayload GetUserTweetsRequest request) {
        GetUserTweetsResponse response = new GetUserTweetsResponse();
        String username = request.getUsername();
        response.getTweet().addAll(tweetRepository.getTweetsByUser(username));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postTweetRequest")
    @ResponsePayload
    public PostTweetResponse postTweet(@RequestPayload PostTweetRequest request) {
        PostTweetResponse response = new PostTweetResponse();
        tweetRepository.addTweet(request.getTweet());
        response.setSuccess(true);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteTweetRequest")
    @ResponsePayload
    public DeleteTweetResponse deleteTweet(@RequestPayload DeleteTweetRequest request) {
        DeleteTweetResponse response = new DeleteTweetResponse();
        int tweetId = request.getId();
        boolean deleted = tweetRepository.deleteTweet(tweetId);
        response.setSuccess(deleted);
        return response;
    }
}
