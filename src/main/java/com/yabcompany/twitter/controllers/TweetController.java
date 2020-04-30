package com.yabcompany.twitter.controllers;

import com.yabcompany.twitter.dto.CreateTweetDto;
import com.yabcompany.twitter.dto.TweetDto;
import com.yabcompany.twitter.exception.NotFoundException;
import com.yabcompany.twitter.models.Thread;
import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;
import com.yabcompany.twitter.repositories.ThreadRepository;
import com.yabcompany.twitter.repositories.UserRepository;
import com.yabcompany.twitter.services.CreateTweetService;
import com.yabcompany.twitter.services.ThreadService;
import com.yabcompany.twitter.services.TweetService;
import com.yabcompany.twitter.util.NumberFactsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

    @Autowired
    private NumberFactsApi numberFactsApi;

    @GetMapping("/{id}")
    public String getTweetPage(@PathVariable("id") Tweet tweet, Model model, Principal principal) {

        if (principal != null) {
            User user = userRepository.findUserByUsername(principal.getName()).get();
            model.addAttribute("currentUser", user);

            if (tweet.getLikes().contains(user)) {
                model.addAttribute("liked", true);
            } else {
                model.addAttribute("liked", false);
            }

        }

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
                return "redirect:" + MvcUriComponentsBuilder.fromMappingName("SC#getLoginPage").build();
            }
            return "redirect:" +
                    MvcUriComponentsBuilder.fromMappingName("UC#getUserPage").arg(0, principal.getName()).build();
        } else {
            map.addAttribute("form", createTweetDto);
            return "tweets_block/create_tweet";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("form", new TweetDto());

        /*
            Add random fact using API
         */
        String fact = numberFactsApi.getRandomFact();
        model.addAttribute("randomFact", fact);


        return "tweets_block/create_tweet";
    }


    @PostMapping("/{id}/like")
    public String addLike(
            @PathVariable("id") Tweet tweet,
            Model map,
            Principal principal
    ) {
        Optional<User> user = userRepository.findUserByUsername(principal.getName());

        if (user.isPresent()) {
            tweetService.addLike(tweet, user.get());
        } else {
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("SC#getLoginPage").build();
        }
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("TC#getTweetPage").arg(0, tweet).build();
    }
    @PostMapping("/{id}/remove-like")
    public String removeLike(
            @PathVariable("id") Tweet tweet,
            Principal principal
    ) {
        Optional<User> user = userRepository.findUserByUsername(principal.getName());

        if (user.isPresent()) {
            tweetService.removeLike(tweet, user.get());
        } else {
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("SC#getLoginPage").build();
        }
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("TC#getTweetPage").arg(0, tweet).build();
    }

    @PostMapping("/{id}/reply")
    public String replyTweet(Principal principal,
                             @PathVariable("id") Tweet mainTweet,
                             Model map,
                             @Valid @ModelAttribute("form") CreateTweetDto createTweetDto,
                             BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {

            Optional<User> user = userRepository.findUserByUsername(principal.getName());

            if (user.isPresent()) {
                Tweet tweet = createTweetService.createTweet(createTweetDto, user.get());
                Thread thread = threadService.getThreadById(mainTweet.getId());
                threadService.replyTweet(thread, tweet);
                threadService.createThead(tweet);

            } else {
                return "redirect:" + MvcUriComponentsBuilder.fromMappingName("SC#getLoginPage").build();
            }
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("TC#getTweetPage").arg(0, mainTweet).build();
        } else {
            map.addAttribute("form", createTweetDto);
            return MvcUriComponentsBuilder.fromMappingName("TC#getTweetPage").arg(0, mainTweet).build();
        }

    }

    @PostMapping("{id}/delete")
    public String deleteTweet(Principal principal,
                              @PathVariable("id") Tweet mainTweet,
                              Model map) {
        Optional<User> user = userRepository.findUserByUsername(principal.getName());

        if (user.isPresent() && mainTweet.getAuthor().equals(user.get())) {
            tweetService.deleteTweet(mainTweet);
        }
        return "redirect:" +
                MvcUriComponentsBuilder.fromMappingName("UC#getUserPage").arg(0, principal.getName()).build();
    }

    @GetMapping("{id}/update")
    public String updateTweetPage(Principal principal,
                                  @PathVariable("id") Tweet mainTweet,
                                  Model model) {
        Optional<User> user = userRepository.findUserByUsername(principal.getName());

        if (user.isPresent() && mainTweet.getAuthor().equals(user.get())) {
            model.addAttribute("form", TweetDto.builder().tweetText(mainTweet.getTweetText()).build());
        }
        return "tweets_block/update_tweet";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("{id}/update")
    public String updateTweet(Principal principal,
                              Model map,
                              @PathVariable("id") Tweet mainTweet,
                              @Valid @ModelAttribute("form") CreateTweetDto createTweetDto,
                              BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {

            Optional<User> user = userRepository.findUserByUsername(principal.getName());

            if (user.isPresent()) {
                Tweet tweet = createTweetService.updateTweet(createTweetDto, mainTweet);
            } else {
                return "redirect:" + MvcUriComponentsBuilder.fromMappingName("SC#getLoginPage").build();
            }
            return "redirect:" +
                    MvcUriComponentsBuilder.fromMappingName("UC#getUserPage").arg(0, principal.getName()).build();
        } else {
            map.addAttribute("form", createTweetDto);
            return "tweets_block/update_tweet";
        }
    }
}
