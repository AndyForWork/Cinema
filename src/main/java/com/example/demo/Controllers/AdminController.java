package com.example.demo.Controllers;

import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/admin")
    public String mainAdmin(Model model) {
        return "admin";
    }
    @GetMapping("/admin/users")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "allUsers";
    }

    @GetMapping("/admin/del")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              Model model) {
        logger.info(String.valueOf(userId));

        userService.deleteUser(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/get/{userId}")
    public String  getUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }
}
