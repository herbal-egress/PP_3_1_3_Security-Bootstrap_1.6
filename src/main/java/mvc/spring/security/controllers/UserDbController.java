package mvc.spring.security.controllers;

import mvc.spring.security.dao.UserDbDAO;
import mvc.spring.security.entities.Role;
import mvc.spring.security.entities.User;
import mvc.spring.security.model.UserDb;
import mvc.spring.security.repositories.RoleRepository;
import mvc.spring.security.repositories.UserRepository;
import mvc.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class UserDbController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDbDAO userDbDAO;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/start") // страница для всех. http://localhost:8080/start
    public String pageForAll(Model model) {
        model.addAttribute("startkey", userDbDAO.allUsers());
        return "usersview/start_page";
    }

    // возвращает всех юзеров
    @GetMapping("/admin") // http://localhost:8080/admin
    public ModelAndView allUsersDb(Model model, Principal principal) {
        User user = new User();
        ModelAndView mav = new ModelAndView("usersview/admin_page");
        mav.addObject("new_user", user);
// ищем все роли, указанные в форме и назначаем их новому юзеру:
        List<Role> roles = roleRepository.findAll();
        mav.addObject("allRoles", roles);
        model.addAttribute("all_users", userRepository.findAll());
        model.addAttribute("all_usersDb", userDbDAO.allUsers());
// через principal получаем полную сущность авторизованного юзера по имени этого юзера:
        User userPr = userService.findByUsername(principal.getName());
        model.addAttribute("auth_user", userPr);
        return mav;
    }

    // принимает POST-запрос, и ДОБАВЛЯЕТ нового юзера в БД с помощью DAO:
    @PostMapping("/admin/users")
    public String createUserDb(@ModelAttribute("createkey") UserDb userDb) {
        userDbDAO.save(userDb);
        return "redirect:/admin/users";
    }

    // отправляет запрос в БД для записи отредактированного юзера:
    @PatchMapping("/admin/users/id")
    public ResponseEntity<?> updateUserDb(@RequestParam int id, @RequestBody UserDb userDb) {
        userDbDAO.update(id, userDb);
        return ResponseEntity.ok().build();
    }

    // удаление человека по id на странице /admin/users/id?id=
    @DeleteMapping("/admin/users/id")
    public ResponseEntity<?> deleteUserDb(@RequestParam int id) {
        userDbDAO.delete(id);
        return ResponseEntity.ok().build();
    }
}