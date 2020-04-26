package com.yabcompany.twitter.repositories;

import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findAllByAuthor(User user);

    Page<Tweet> findAllByAuthor(Pageable var, User user);
}
