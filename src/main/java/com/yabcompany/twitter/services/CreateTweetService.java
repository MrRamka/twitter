package com.yabcompany.twitter.services;

import com.yabcompany.twitter.dto.CreateTweetDto;
import com.yabcompany.twitter.models.User;

public interface CreateTweetService {
    void createTweet(CreateTweetDto createTweetDto, User author);
}
