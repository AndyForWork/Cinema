package com.example.demo.Controllers;

import com.example.demo.Entity.Client;
import com.example.demo.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ClientController {

    @Autowired
    private UserRepository userRepository;

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ClientController.class);

    @GetMapping("/account")
    public ModelAndView accountGet(@RequestParam(required = true) Long userId, Model model){
        ModelAndView mav = new ModelAndView("mainAccount");
        if (userRepository.findById(userId).isPresent())
            mav.addObject("client", userRepository.findById(userId).get());
        //нужно что-то сделатб если такого id не существует
        return mav;
    }

    @PostMapping("/account")
    public RedirectView accountPost( Long id,  Long money){
        logger.info(String.valueOf(userRepository.findById(id).get()));
        userRepository.findById(id).get().setMoney(userRepository.findById(id).get().getMoney() + money);
        userRepository.save(userRepository.findById(id).get());
        logger.info(String.valueOf(userRepository.findById(id).get()));
        return new RedirectView("/account?userId=" +id );
    }
}
