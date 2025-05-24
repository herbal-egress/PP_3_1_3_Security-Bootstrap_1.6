package mvc.spring.security.controllers;

import mvc.spring.security.entities.Role;
import mvc.spring.security.entities.User;
import mvc.spring.security.services.RoleService;
import mvc.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/start")
    public String pageForAll(Model model) {
        model.addAttribute("start_key", userService.findAllUser());
        return "usersview/start_page";
    }

    @GetMapping("/admin")
    public ModelAndView pageForAdmin(Model model, Principal principal) {
        User user = new User();
        ModelAndView mav = new ModelAndView("usersview/admin_page");
        mav.addObject("new_user", user);
        List<Role> roles = roleService.findAllRole();
        mav.addObject("all_roles", roles);
        model.addAttribute("all_users", userService.findAllUser());
        User userPr = userService.findUserByName(principal.getName());
        model.addAttribute("auth_user", userPr);
        return mav;
    }

    @GetMapping("/user")
    public String pageForAuthenticatedUsers(Model model, Principal principal) {
        User user = userService.findUserByName(principal.getName());
        model.addAttribute("authname_key", user);
        model.addAttribute("all_users", userService.findAllUser());
        return "usersview/user_page";
    }


    @GetMapping("/admin/id")
    public String userById(@RequestParam int id, Model model) {
        model.addAttribute("userById_key", userService.findUserById(id));
        return "usersview/userById";
    }

    @GetMapping("/admin/edit/id")
    public String editUser(@RequestParam int id, Model model) {
        model.addAttribute("edit_key", userService.findUserById(id));
        List<Role> roles = roleService.findAllRole();
        model.addAttribute("allRoles", roles);
        return "usersview/edit";
    }

    @PostMapping("/admin/save")
    public String registrationUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.register(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/id")
    public ResponseEntity<?> updateUser(@RequestParam int id, @RequestBody User user) {
        userService.update(id, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/id")
    public ResponseEntity<?> deleteUser(@RequestParam int id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}