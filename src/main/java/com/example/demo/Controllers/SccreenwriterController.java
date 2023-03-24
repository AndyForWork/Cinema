package com.example.demo.Controllers;

import com.example.demo.Entity.Screenwriter;
import com.example.demo.Repository.ScreenwriterRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SccreenwriterController {

    @Autowired
    private ScreenwriterRepository screenwriterRepository;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);
    @GetMapping("/screenwriter/add")
    public String addScreenwriterGet(Screenwriter screenwriter){
        logger.info("Started");
        return "addScreenwriter";
    }

    @PostMapping("/screenwriter/add")
    public String addScreenwriterPost(@Valid Screenwriter screenwriter){
        logger.info("post started");
        logger.info(screenwriter.toString());
        screenwriterRepository.save(screenwriter);
        return "trash";
    }

    @GetMapping("/screenwriter/all")
    public ModelAndView allScreenwriter(){
        ModelAndView mav = new ModelAndView("allScreenwriter");
        mav.addObject("screenwriter",screenwriterRepository.findAll());
        return mav;
    }
}
