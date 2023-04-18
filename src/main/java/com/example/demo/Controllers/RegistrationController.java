package com.example.demo.Controllers;

import com.example.demo.Entity.Client;
import com.example.demo.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);

    @GetMapping("/registration")
    public ModelAndView registration(Client client) {
        logger.info("reg start");
        ModelAndView mav = new ModelAndView("registration");
        return mav;
    }

    @PostMapping("/registration")
    public Object addUser(@Valid Client client, BindingResult bindingResult, Model model) {
        logger.info("registering" + client);


        if (!client.getPassword().equals(client.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return new ModelAndView("registration");
        }
        if (!userService.saveUser(client)){
            model.addAttribute("clientNameError", "Пользователь с таким именем уже существует");
            return new ModelAndView("registration");
        }

        return new RedirectView("/film/all");
    }
}
