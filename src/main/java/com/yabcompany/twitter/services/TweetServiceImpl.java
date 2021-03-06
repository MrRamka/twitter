package com.yabcompany.twitter.services;

import com.yabcompany.twitter.dto.TweetDto;
import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;
import com.yabcompany.twitter.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TweetServiceImpl implements TweetService {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Tweet> getAllTweets(Integer page, Integer size, String property) {
        return null;
    }

    /**
     * Add tweet from Dto and author
     *
     * @param tweetData
     * @param author
     * @return Tweet
     */
    @Override
    public Tweet addTweet(TweetDto tweetData, User author) {
        Tweet tweet = Tweet
                .builder()
                .tweetText(tweetData.getTweetText())
                .author(author)
                .createdAt(LocalDateTime.now())
                .build();
        tweetRepository.save(tweet);
        return tweet;
    }

    /**
     * Get single tweet using ID
     *
     * @param tweetId
     * @return TweetDto
     * @see TweetDto
     */
    @Override
    public Tweet getTweet(Long tweetId) {
        return tweetRepository.getOne(tweetId);
    }


    /**
     * Get all users tweets
     *
     * @param author
     * @return List on TweetDto
     * @see TweetDto
     */
    @Override
    public List<Tweet> getUsersAllTweets(User author) {
        List<Tweet> tweets = tweetRepository.findAllByAuthor(author);
        return tweets;
    }

    /**
     * Paginated user follows tweet query set
     *
     * @param user
     * @param page
     * @param size
     * @param property
     * @return
     */
    @Override
    public List<Tweet> getUserFollowsTweets(User user, Integer page, Integer size, String property) {
        Set<User> follows = userService.getFollowsUsers(user);
        List<Tweet> fullFollowsTweets = new ArrayList<>();
        for (User user_iter : follows) {
            fullFollowsTweets.addAll(
                    getUserTweets(page, size, property, user_iter)
            );
        }
        return fullFollowsTweets;
    }

    /**
     * Get users paginated tweet set
     *
     * @param page
     * @param size
     * @param property
     * @param user
     * @return
     */
    @Override
    public List<Tweet> getUserTweets(Integer page, Integer size, String property, User user) {
        Sort sort = Sort.by(property);
        PageRequest request = PageRequest.of(page, size, sort);
        Page<Tweet> pageResult = tweetRepository.findAllByAuthor(request, user);
        List<Tweet> tweets = pageResult.getContent();
        return tweets;
    }

    /**
     * Delete tweet from DataBase
     *
     * @param tweet
     */
    @Override
    public void deleteTweet(Tweet tweet) {

        tweetRepository.delete(tweet);
    }

    /**
     * Add like to tweet
     *
     * @param tweet
     * @param user
     * @return
     */
    @Override
    public Tweet addLike(Tweet tweet, User user) {
        tweet.getLikes().add(user);
        tweetRepository.save(tweet);
        return tweet;
    }

    /**
     * Remove like to tweet
     *
     * @param tweet
     * @param user
     * @return
     */
    @Override
    public Tweet removeLike(Tweet tweet, User user) {
        tweet.getLikes().remove(user);
        tweetRepository.save(tweet);
        return tweet;
    }
}
