package com.yabcompany.twitter.services;

import com.yabcompany.twitter.dto.CreateTweetDto;
import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;

public interface CreateTweetService {
    Tweet createTweet(CreateTweetDto createTweetDto, User author);

    Tweet updateTweet(CreateTweetDto createTweetDto, Tweet tweet);
}
