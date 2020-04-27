package com.yabcompany.twitter.services;

import com.yabcompany.twitter.dto.CreateTweetDto;
import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;
import com.yabcompany.twitter.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateTweetServiceImpl implements CreateTweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet createTweet(CreateTweetDto createTweetDto, User author) {
        Tweet tweet = Tweet
                .builder()
                .author(author)
                .createdAt(LocalDateTime.now())
                .tweetText(createTweetDto.getTweetText())
                .build();
        tweetRepository.save(tweet);
        return tweet;
    }
}
