package com.example.demo.Controllers;

import com.example.demo.Entity.Screenwriter;
import com.example.demo.Repository.ScreenwriterRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SccreenwriterController {

    @Autowired
    private ScreenwriterRepository screenwriterRepository;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);
    @GetMapping("/screenwriter/add")
    public ModelAndView addScreenwriterGet(Screenwriter screenwriter, @PathVariable(required = false) Long id){
        logger.info("starting GET screenwriter add");
        ModelAndView mav = new ModelAndView("alScreenwriter");
        if (id!=null && screenwriterRepository.findById(id).isPresent()){
            logger.info("found id");
            logger.info(screenwriterRepository.findById(id).get().toString());
            mav.addObject("screenwriter", screenwriterRepository.findById(id).get());
        }
        return mav;
    }

    @PostMapping("/screenwriter/add")
    public String addScreenwriterPost(@Valid Screenwriter screenwriter){
        logger.info("post started");
        logger.info(screenwriter.toString());
        screenwriterRepository.save(screenwriter);
        return "trash";
    }

    @GetMapping("screenwriter/del")
    public void delScreenwriterGet(@RequestParam(required = true) Long id){
        screenwriterRepository.deleteById(id);

    }

    @GetMapping("/screenwriter/all")
    public ModelAndView allScreenwriter(){
        ModelAndView mav = new ModelAndView("allScreenwriter");
        mav.addObject("screenwriter",screenwriterRepository.findAll());
        return mav;
    }
}
