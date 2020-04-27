package com.yabcompany.twitter.services;

import com.yabcompany.twitter.models.Thread;
import com.yabcompany.twitter.models.Tweet;

public interface ThreadService {

    Thread getThreadByMainTweet(Tweet tweet);

    Thread getThreadById(Long id);

    Thread replyTweet(Tweet mainTweet, Tweet replyingTweet);

    Thread replyTweet(Thread thread, Tweet replyingTweet);

    Thread createThead(Tweet mainTweet);
}
