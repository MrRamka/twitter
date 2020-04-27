package com.yabcompany.twitter.repositories;

import com.yabcompany.twitter.models.Thread;
import com.yabcompany.twitter.models.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
    Thread getThreadByMainTweet_Id(Long mainTweet_id);
}
