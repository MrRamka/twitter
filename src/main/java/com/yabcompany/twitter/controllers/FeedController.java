package com.yabcompany.twitter.controllers;


import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;
import com.yabcompany.twitter.repositories.UserRepository;
import com.yabcompany.twitter.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class FeedController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetService tweetService;

    private static final int FIRST_PAGE = 0;

    private static final int PAGE_AMOUNT = 10;

    private static final String SORTING_TYPE = "createdAt";

    @Autowired
    private DateTimeFormatter formatter;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/feed")
    public String getFeedPage(Model model, Principal principal) {

        Optional<User> currentUser = userRepository.findUserByUsername(principal.getName());

        if (currentUser.isPresent()) {
            List<Tweet> tweets = tweetService.getUserFollowsTweets(
                    currentUser.get(),
                    FIRST_PAGE,
                    PAGE_AMOUNT,
                    SORTING_TYPE
            );

            model.addAttribute("tweets", tweets);
            model.addAttribute("formatter", formatter);
            return "tweets_block/feed";
        } else {
            return "redirect:/login";
        }

    }
}
