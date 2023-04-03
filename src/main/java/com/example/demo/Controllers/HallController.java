package com.example.demo.Controllers;

import com.example.demo.Entity.Hall;
import com.example.demo.Entity.HallType;
import com.example.demo.Repository.HallRepository;
import com.example.demo.Repository.HallTypeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HallController {

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private HallTypeRepository hallTypeRepository;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);

    @GetMapping("/hall/add")
    private ModelAndView addHallGet(Hall hall, @RequestParam(required = false) Long id){
        ModelAndView mav = new ModelAndView("addHall");
        mav.addObject("hallTypes", hallTypeRepository.findAll());
        if (id!=null && hallRepository.findById(id).isPresent()){
            mav.addObject("getHall", hallRepository.findById(id).get());
        }
        return mav;
    }

    @PostMapping("/hall/add")
    private RedirectView addHallPost(@Valid Hall hall, @Valid Long hallTypeId){
        logger.info(hall.toString());
        logger.info(String.valueOf(hallTypeId));
        if (hallTypeRepository.findById(hallTypeId).isPresent())
            hall.setHallType(hallTypeRepository.findById(hallTypeId).get());
        if (hall.getId()!=null){
            Hall prev = hallRepository.findById(hall.getId()).get();
            prev.setRows(hall.getRows());
            prev.setName(hall.getName());
            prev.setSeatsInRow(hall.getSeatsInRow());
            prev.changeHallType(hall.getHallType());
            hallRepository.save(prev);
        }
        else{
            hallRepository.save(hall);
        }

        return new RedirectView("all");
    }

    @GetMapping("/hall/all")
    private ModelAndView allHallGet(){

        ModelAndView mav = new ModelAndView("allHall");
        mav.addObject("halls",hallRepository.findAll());
        return mav;
    }

    @GetMapping("/hall/del")
    private RedirectView dellHallGet(@RequestParam(required = true) Long id){
        hallRepository.deleteById(id);
        return new RedirectView("all");
    }

}
