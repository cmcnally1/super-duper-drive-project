package com.cmcnally.udacity.project.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
    Login controller to get the login view and handle user input when logging in
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginView() {
        return "login";
    }

}
