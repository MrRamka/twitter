package com.yabcompany.twitter.controllers;

import com.yabcompany.twitter.dto.TweetDto;
import com.yabcompany.twitter.dto.UserDto;
import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;
import com.yabcompany.twitter.repositories.TweetRepository;
import com.yabcompany.twitter.repositories.UserRepository;
import com.yabcompany.twitter.services.TweetService;
import com.yabcompany.twitter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    @GetMapping("/{username}")
    public String getUserPage(@PathVariable String username, Model model, Principal principal) {
        Optional<User> user = userRepository.findUserByUsername(username);

        if (principal != null) {
            model.addAttribute("currUsername", principal.getName());
        }

        if (user.isPresent()) {
            UserDto userDto = UserDto.from(user.get());
            model.addAttribute("user", user.get());

            // Get users Tweets
            List<Tweet> userTweets = tweetService.getUsersAllTweets(user.get());
            model.addAttribute("userTweets", userTweets);
// TODO Check following
            // Date Time Formatter
            model.addAttribute("formatter", dateTimeFormatter);

            // Followers Amount
            int followerAmount = 0;
            model.addAttribute("followerAmount", followerAmount);
        } else {
            throw new UsernameNotFoundException("Cant find user");
        }

        return "user_page";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{username}/follow")
    public String followUser(Principal principal, Model model, @PathVariable("username") String targetUsername) {
        userService.addFollower(targetUsername, principal.getName());
        return "redirect:/users/" + targetUsername;
    }


}
