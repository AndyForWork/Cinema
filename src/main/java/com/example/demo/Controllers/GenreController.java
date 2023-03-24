package com.example.demo.Controllers;

import com.example.demo.Entity.Genre;
import com.example.demo.Repository.GenreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GenreController {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/genre/add")
    public String addGenre(Genre genre){
        logger.info("Started");
        return "addGenre";
    }

    @GetMapping("/genre/all")
    public String allGenre(){
        logger.info(genreRepository.findAll().toString());
        return "trash";
    }
    @PostMapping("/genre/add")
    public String getGenre(@Valid Genre genre, Model model){
        logger.info(genre.toString());
        genreRepository.save(genre);
        //return genreRepository.findAll().toString();
        return "trash";
    }
}
