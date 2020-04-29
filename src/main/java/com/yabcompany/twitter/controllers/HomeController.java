package com.yabcompany.twitter.controllers;

import com.yabcompany.twitter.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private SignUpService service;

    @GetMapping
    public String getHomePage(Authentication authentication, Principal principal) {
        if (authentication != null) {
            String username = principal.getName();
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("FC#getFeedPage").build();
        } else {

            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("SC#getLoginPage").build();
        }
    }
}
