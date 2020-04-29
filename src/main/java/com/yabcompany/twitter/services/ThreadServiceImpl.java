package com.yabcompany.twitter.services;

import com.yabcompany.twitter.models.Thread;
import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.repositories.ThreadRepository;
import com.yabcompany.twitter.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("twitterThread")
public class ThreadServiceImpl implements ThreadService {

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Thread getThreadByMainTweet(Tweet tweet) {
        return threadRepository.getThreadByMainTweet_Id(tweet.getId());
    }

    @Override
    public Thread getThreadById(Long id) {
        return threadRepository.getThreadByMainTweet_Id(id);
    }

    @Override
    public Thread replyTweet(Tweet mainTweet, Tweet replyingTweet) {
        Thread thread = getThreadByMainTweet(mainTweet);
        thread.getRepliedTweets().add(replyingTweet);
        threadRepository.save(thread);
        return thread;
    }

    @Override
    public Thread replyTweet(Thread thread, Tweet replyingTweet) {
        if (thread.getRepliedTweets() != null) {
            thread.getRepliedTweets().add(replyingTweet);
            replyingTweet.setRepliedTweet(thread);
            tweetRepository.save(replyingTweet);
        }
        return thread;
    }

    @Override
    public Thread createThead(Tweet mainTweet) {
        Thread t = Thread
                .builder()
                .mainTweet(mainTweet)
                .build();
        threadRepository.save(t);
        return t;
    }

}
