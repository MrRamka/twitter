package com.yabcompany.twitter.controllers;

import com.yabcompany.twitter.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private SignUpService service;

    @GetMapping
    public String getHomePage(Authentication authentication, Principal principal) {
        if (authentication != null) {
            String username = principal.getName();
            return "redirect:/feed";
        } else {

            return "redirect:/login";
        }
    }
}
