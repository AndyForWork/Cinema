package com.example.demo.Controllers;

import com.example.demo.Entity.Cinema;
import com.example.demo.Entity.Hall;
import com.example.demo.Repository.CinemaRepository;
import com.example.demo.Repository.HallRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

@Controller
public class CinemaController {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private HallRepository hallRepository;


    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);

    @GetMapping("/cinema/add")
    private ModelAndView addCinemaGet(Cinema cinema, @RequestParam(required = false) Long id){
        ModelAndView mav = new ModelAndView("addCinema");
        mav.addObject("halls", hallRepository.findAll());
        if (id !=null && cinemaRepository.findById(id).isPresent()) {
            mav.addObject("getCinema", cinemaRepository.findById(id).get());
        }
        return mav;
    }

    @PostMapping("/cinema/add")
    private RedirectView addCinemaPost(@Valid Cinema cinema, @Valid Long[] hallIds){
        logger.info(String.valueOf(cinema));
        logger.info(Arrays.toString(hallIds));

        cinema.clearHalls();
        if (hallIds!=null) {
            for (Long hallId : hallIds) {
                if (hallRepository.findById(hallId).isPresent()) {
                    Hall prev = hallRepository.findById(hallId).get();
                    prev.setCinema(cinema);
                    prev.getCinema().getHalls().remove(prev);
                    cinema.addHalls(hallRepository.findById(hallId).get());

                }
            }
        }
        logger.info(String.valueOf(cinema));
        if (cinema.getId() != null && cinemaRepository.findById(cinema.getId()).isPresent()){
            Cinema prev = cinemaRepository.findById(cinema.getId()).get();
            prev.setName(cinema.getName());
            prev.setInfo(cinema.getInfo());
            prev.setLocation(cinema.getLocation());
            prev.changeHalls(cinema.getHalls());
            cinemaRepository.save(prev);
        }
        else{
            cinemaRepository.save(cinema);
        }
        return new RedirectView("all");
    }

    @GetMapping("/cinema/all")
    private ModelAndView allCinemaGet(){
        ModelAndView mav = new ModelAndView("allCinema");
        mav.addObject("cinemas", cinemaRepository.findAll());
        return mav;
    }

    @GetMapping("/cinema/del")
    private RedirectView delCinemaGet(@RequestParam(required = true) Long id){

        //удалить, когда нормально доделаю логику кинотеатра чтоб с кинотеатром удалялись залы
        for (Hall hall: cinemaRepository.findById(id).get().getHalls()) {
            hall.setCinema(null);
            hallRepository.save(hall);
        }
        Cinema prev = cinemaRepository.findById(id).get();
        prev.clearHalls();
        cinemaRepository.save(prev);
        cinemaRepository.deleteById(id);
        return new RedirectView("all");
    }
}
