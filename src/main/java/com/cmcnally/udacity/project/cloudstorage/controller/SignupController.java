package com.cmcnally.udacity.project.cloudstorage.controller;

import com.cmcnally.udacity.project.cloudstorage.model.User;
import com.cmcnally.udacity.project.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
    Signup controller to get the signup view and handle user input when signing up
 */

@Controller
@RequestMapping("/signup")
public class SignupController {

    // User service used to create the user
    private final UserService userService;

    // Constructor
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    // Method to get the signup view
    @GetMapping
    public String signupView(){
        return "signup";
    }

    // Method to handle the POST request from the user when they sign up
    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model){

        // Variable to hold an error message to display
        String signupError = null;

        // Check if the username is available. If not, set an error message
        if(!userService.isUsernameAvailable(user.getUsername())){
            signupError = "The username already exists. Please choose another and try again.";
        }

        // Check if an error has occurred already. If not, proceed to create the user.
        if (signupError == null) {
            int rowsAdded = userService.createUser(user);

            // Check if user was created. If not, set an error to be displayed
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        // Check if an error has occurred. If not, set success.
        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
