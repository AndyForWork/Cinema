package com.example.demo.Controllers;

import com.example.demo.Entity.Film;
import com.example.demo.Entity.Genre;
import com.example.demo.Repository.FilmRepository;
import com.example.demo.Repository.GenreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class GenreController {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/genre/add")
    public ModelAndView addGenre(Genre genre ,@RequestParam(required = false) Long id){
        logger.info("start get genre add");

        ModelAndView mav = new ModelAndView("addGenre");
        if (id!=null && genreRepository.findById(id).isPresent()) {
            logger.info("okkkkk");
            logger.info(String.valueOf(genreRepository.findById(id).get()));
            mav.addObject("getGenre", genreRepository.findById(id).get());
        }
        return mav;
    }

    @PostMapping("/genre/add")
    public RedirectView getGenre(@Valid Genre genre){
        logger.info("starting GET genre all");
        if (genre.getId()!=null)
        {
            Genre prev = genreRepository.findById(genre.getId()).get();
            prev.setName(genre.getName());
            genreRepository.save(prev);
        }
        else{
            logger.info(genre.toString());
            genreRepository.save(genre);
        }
        return new RedirectView("all");
    }

    @GetMapping("/genre/del")
    public ModelAndView delGenreGet(@RequestParam(required = true) Long id){
        List<Film> films = (List<Film>) filmRepository.findAll();
        for(Film film: films){
            if (film.getGenre()!=null && film.getGenre().getId()==id){
                film.setGenre(null);
                filmRepository.save(film);
            }
        }
        genreRepository.deleteById(id);
        ModelAndView mav = new ModelAndView("redirect:/genre/all");
        return mav;
    }

    @GetMapping("/genre/all")
    public ModelAndView allGenreGet(){
        ModelAndView mav = new ModelAndView("allGenre");
        mav.addObject("genres",genreRepository.findAll());
        return mav;
    }
}
