package com.yabcompany.twitter.controllers;

import com.yabcompany.twitter.dto.SignUpDto;
import com.yabcompany.twitter.oath.VkOauth;
import com.yabcompany.twitter.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;

@Controller
public class SecurityController {

    @Autowired
    private SignUpService service;

    @Autowired
    private VkOauth vkOauth;

    @GetMapping("/login")
    public String getLoginPage(Model model) throws MalformedURLException {
        model.addAttribute("vkLogin", vkOauth.getLoginUrl());
        return "security/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model map) {
        map.addAttribute("form", new SignUpDto());
        return "security/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("form") SignUpDto form,
            BindingResult bindingResult,
            Model map
    ) {
        if (!bindingResult.hasErrors()) {
            service.signUp(form);
            return "redirect:" +  MvcUriComponentsBuilder.fromMappingName("SC#getRegisterPage").build();
        } else {
            map.addAttribute("form", form);
            return "security/register";
        }
    }

    @GetMapping("/vklogin")
    public String enterWithVk(@RequestParam(value = "code", required = false) String code) throws IOException {
        System.out.println(code);
        if (code != null) {
//            System.out.println(vkOauth.sendRequest(vkOauth.getAccessToken(code)));
            return "redirect:" + vkOauth.getAccessToken(code).toString();
        }
        return "home";

    }

    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/accessDenied")
    public String getError403Page() {
        return "errors/403";
    }
}
