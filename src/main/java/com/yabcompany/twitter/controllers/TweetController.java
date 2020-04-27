package com.yabcompany.twitter.controllers;

import com.yabcompany.twitter.dto.CreateTweetDto;
import com.yabcompany.twitter.dto.TweetDto;
import com.yabcompany.twitter.models.Thread;
import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;
import com.yabcompany.twitter.repositories.ThreadRepository;
import com.yabcompany.twitter.repositories.UserRepository;
import com.yabcompany.twitter.services.CreateTweetService;
import com.yabcompany.twitter.services.ThreadService;
import com.yabcompany.twitter.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private CreateTweetService createTweetService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    @Qualifier("twitterThread")
    private ThreadService threadService;

    @Autowired
    private ThreadRepository threadRepository;

    @GetMapping("/{id}")
    public String getTweetPage(@PathVariable Long id, Model model) {
        Tweet tweet = tweetService.getTweet(id);
        model.addAttribute("tweet", tweet);
        // Date Time Formatter
        model.addAttribute("formatter", dateTimeFormatter);
        model.addAttribute("form", new CreateTweetDto());

        Thread replyThread = threadService.getThreadByMainTweet(tweet);

        if (replyThread != null) {
            //Replies
            model.addAttribute("replies", replyThread.getRepliedTweets());
        }

        return "tweets_block/single_tweet_page";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createTweet(Principal principal,
                              Model map,
                              @Valid @ModelAttribute("form") CreateTweetDto createTweetDto,
                              BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {
            map.addAttribute("noErrors", true);

            Optional<User> user = userRepository.findUserByUsername(principal.getName());

            if (user.isPresent()) {
                Tweet tweet = createTweetService.createTweet(createTweetDto, user.get());
                threadService.createThead(tweet);
            } else {
                return "redirect:/login";
            }
            return "redirect:/users/" + principal.getName();
        } else {
            map.addAttribute("form", createTweetDto);
            return "tweets_block/create_tweet";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    private String getCreatePage(Model model) {
        model.addAttribute("form", new TweetDto());
        return "tweets_block/create_tweet";
    }

    @PostMapping("/addLike")
    @ResponseBody
    public String addLike(Principal principal) {

        return "";
    }

    @PostMapping("/{id}/reply")
    public String replyTweet(Principal principal,
                             @PathVariable("id") Long id,
                             Model map,
                             @Valid @ModelAttribute("form") CreateTweetDto createTweetDto,
                             BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {

            Optional<User> user = userRepository.findUserByUsername(principal.getName());

            if (user.isPresent()) {
                Tweet tweet = createTweetService.createTweet(createTweetDto, user.get());
                Thread thread = threadService.getThreadById(id);
                threadService.replyTweet(thread, tweet);
                threadService.createThead(tweet);

            } else {
                return "redirect:/login";
            }
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("TC#getTweetPage").arg(0, id).build();
        } else {
            map.addAttribute("form", createTweetDto);
            return MvcUriComponentsBuilder.fromMappingName("TC#getTweetPage").arg(0, id).build();
        }

    }


}
