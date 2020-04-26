package com.yabcompany.twitter.controllers;

import com.yabcompany.twitter.dto.SignUpDto;
import com.yabcompany.twitter.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class SecurityController {

    @Autowired
    private SignUpService service;

    @GetMapping("/login")
    public String getLoginPage() {
        return "security/login";
    }

    @GetMapping("/register")
    private String getRegisterPage(Model map) {
        map.addAttribute("form", new SignUpDto());
        return "security/register";
    }

    @PostMapping("/register")
    private String register(@Valid @ModelAttribute("form") SignUpDto form, BindingResult bindingResult, Model map) {
        if (!bindingResult.hasErrors()) {
            service.signUp(form);
            return "redirect:/register";
        } else {
            map.addAttribute("form", form);
            return "security/register";
        }
    }




}
