package com.example.springsecurity.contriller;

import com.example.springsecurity.model.User;
import com.example.springsecurity.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("admin/user-create")
    public String createUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "user-create";

    }
    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUserName(principal.getName()));
        return "user";
    }
    @GetMapping("/admin/info")
    public String adminPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUserName(principal.getName()));
        return "admin-info";
    }

    @PostMapping("admin/user-create")
    public String createUser(User user, String[] role){
        userService.saveUser(user, role);
        return "redirect:/admin";
    }


    @GetMapping("admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-update";
    }
    @PostMapping("/admin/user-update/{id}")
    public String updateUser(User user, String[] roles){
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

}

