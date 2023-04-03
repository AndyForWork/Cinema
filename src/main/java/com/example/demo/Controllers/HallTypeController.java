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

import java.util.List;

@Controller
public class HallTypeController {

    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private HallTypeRepository hallTypeRepository;


    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);

    @GetMapping("/hall/type/add")
    private ModelAndView addHallTypeGet(HallType hallType, @RequestParam(required = false) Long id){
        logger.info("HallType add GET started");
        ModelAndView mav = new ModelAndView("addHallType");
        if (id!=null && hallTypeRepository.findById(id).isPresent()){
            logger.info("editing existing hallType");
            mav.addObject("getHallType", hallTypeRepository.findById(id).get());
        }

        return mav;
    }

    @PostMapping("/hall/type/add")
    private RedirectView addHallTypePost(@Valid HallType hallType){
        logger.info("HallType add POST started");
        if (hallType.getId()!=null){
            HallType prev = hallTypeRepository.findById(hallType.getId()).get();
            prev.setName(hallType.getName());
            prev.setInfo(hallType.getInfo());
            hallTypeRepository.save(prev);
        }
        else{
            logger.info("new hallType");
            hallTypeRepository.save(hallType);
        }
        return new RedirectView("all");
    }

    @GetMapping("/hall/type/all")
    private ModelAndView allHallTypeGet(){
        ModelAndView mav = new ModelAndView("allHallType");
        mav.addObject("hallTypes", hallTypeRepository.findAll());
        return mav;
    }

    @GetMapping("/hall/type/del")
    private RedirectView delHallTypeGet(@RequestParam(required = true) Long id){
        List<Hall> halls = (List<Hall>) hallRepository.findAll();
        for (Hall hall: halls){
            if (hall.getHallType()!=null && hall.getHallType().getId()==id){
                hall.setHallType(null);
                hallRepository.save(hall);
            }
        }
        hallTypeRepository.deleteById(id);
        return new RedirectView("all");

    }

}
