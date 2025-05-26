package mvc.spring.security.controllers;

import mvc.spring.security.entities.User;

import mvc.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/start")
    public String pageForAll(Model model) {
        model.addAttribute("start_key", userService.findAllUser());
        return "usersview/start_page";
    }

    @GetMapping("/user")
    public String pageForAuthenticatedUsers(Model model, Principal principal) {
        User userPrincipal = userService.findUserByName(principal.getName());
        model.addAttribute("auth_user_key", userPrincipal);
        return "usersview/user_page";
    }
}