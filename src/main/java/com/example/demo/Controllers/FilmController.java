package com.example.demo.Controllers;

import com.example.demo.Entity.Film;
import com.example.demo.Repository.FilmRepository;
import com.example.demo.Repository.GenreRepository;
import com.example.demo.Repository.StaffRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.util.List;

@Controller
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private StaffRepository staffRepository;


    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);
    @GetMapping("/film/add")
    public ModelAndView addFilmGet(Film film ,@RequestParam(required = false) Long id){
        logger.info("starting GET");
        ModelAndView mav = new ModelAndView("addFilm");
        mav.addObject("genres",genreRepository.findAll());
        mav.addObject("staffs",staffRepository.findAll());
        if (id!=null && filmRepository.findById(id).isPresent()) {
            logger.info("okkkkk");
            logger.info(String.valueOf(filmRepository.findById(id).get()));
            mav.addObject("getFilm", filmRepository.findById(id).get());
        }
        return mav;
    }

    @GetMapping("/film/del")
    public ModelAndView delFilmGet(@RequestParam(required = true) Long id){
        filmRepository.deleteById(id);
        ModelAndView mav = new ModelAndView("redirect:/film/all");
        return mav;
    }

    @PostMapping("/film/add")
    public RedirectView addFilmPost(@Valid Film film, @Valid Long genreId){
        logger.info("starting POST");
        logger.info(genreId.toString());
        logger.info(film.toString());
        if (genreRepository.findById(genreId).isPresent()) {
            logger.info("Есть жанр");
            film.setGenre(genreRepository.findById(genreId).get());
        }
        logger.info(film.toString());
        if (film.getId()!=null) {
            Film prev = filmRepository.findById(film.getId()).get();
            //prev.changeGenre(film.getGenre());
            prev.addGenre(film.getGenre());
            prev.setName(film.getName());
            prev.setInfo(film.getInfo());
            prev.setYear(film.getYear());
            prev.changeStaff(film.getStaffs());
            filmRepository.save(prev);
        }
        else {
            logger.info(film.toString());
            filmRepository.save(film);
        }
        return new RedirectView("all");
    }

    @GetMapping("/film/all")
    public ModelAndView allFilmGet(){
        ModelAndView mav = new ModelAndView("allFilm");
        mav.addObject("films",filmRepository.findAll());
        return mav;
    }

}
