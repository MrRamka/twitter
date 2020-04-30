package com.yabcompany.twitter.services;

import com.yabcompany.twitter.dto.TweetDto;
import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;

import java.util.List;

public interface TweetService {

    List<Tweet> getAllTweets(Integer page, Integer size, String sort);

    Tweet getTweet(Long tweetId);

    Tweet addTweet(TweetDto tweetData, User author);

    List<Tweet> getUsersAllTweets(User author);

    List<Tweet> getUserFollowsTweets(User user, Integer page, Integer size, String property);

    List<Tweet> getUserTweets(Integer page, Integer size, String sort, User user);

    void deleteTweet(Tweet tweetId);

    Tweet addLike(Tweet tweet, User user);

    Tweet removeLike(Tweet tweet, User user);
}
