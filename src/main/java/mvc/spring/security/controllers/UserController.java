package mvc.spring.security.controllers;

import mvc.spring.security.dao.UserDbDAO;
import mvc.spring.security.entities.User;
import mvc.spring.security.repositories.UserRepository;
import mvc.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller

public class UserController {
    @Autowired
    private UserDbDAO userDbDAO;
    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // сохраняет нового юзера, приходящего с формы admin_page:
    @PostMapping("/admin/save")
    public String performRegistration(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // действие, если Спринг регистрирует ошибку в bindingResult
            return "redirect:/admin";
        }
        userService.register(user);
        return "redirect:/start";
    }

    @GetMapping("/user") // защищённый адрес. Только админы и юзеры http://localhost:8080/user
    public String pageForAuthenticatedUsers(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("auth_user", user);
        model.addAttribute("all_users", userRepository.findAll());
        model.addAttribute("all_usersDb", userDbDAO.allUsers());
        return "usersview/user_page";
    }
}