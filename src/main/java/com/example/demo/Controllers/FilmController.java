package com.example.demo.Controllers;

import com.example.demo.Entity.Film;
import com.example.demo.Entity.Session;
import com.example.demo.Repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeatRepository seatRepository;


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

    @GetMapping("/film/get")
    public ModelAndView getFilmGet(@RequestParam(required = true) Long id){
        ModelAndView mav = new ModelAndView("getFilm");
        mav.addObject("getFilm", filmRepository.findById(id).get());
        mav.addObject("cinemas", cinemaRepository.findAll());
        List<Session> sessions = ((List<Session>) sessionRepository.findAll()).stream()
                .filter(c -> c.getFilm().getId() == id)
                .collect(Collectors.toList());
        for (Session session : sessions){
            session.getSeats().sort((a,b) -> Math.toIntExact(b.getId() - a.getId()));
        }
        mav.addObject("sessions", sessions);
        return mav;
    }

    @PostMapping("/film/get")
    public ModelAndView getFilmPost(@RequestParam(required = true) Long id, @RequestParam(name="halls", required = false) ArrayList<Long> halls, @Valid Long minPrice, @Valid Long maxPrice, @Valid String dateOfSession ){
        List<Session> res = (List<Session>) sessionRepository.findAll();
        List<Session> result =  res.stream()
                .filter(c -> c.getFilm().getId() == id)
                .filter( c -> (minPrice != null && maxPrice != null) ? c.minCost()>=minPrice && c.maxCost()<=maxPrice : true)
                .filter( c -> (halls != null) ? halls.contains(c.getHall().getId()) : true)
                .filter( c->  {
                    try {
                        return (dateOfSession != "" && dateOfSession!=null) ? c.getStartTime().getDay() == ((DateFormat) new SimpleDateFormat("yyyy-MM-dd")).parse(dateOfSession).getDay() : true;
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        ModelAndView mav = new ModelAndView("getFilm");
        mav.addObject("getFilm", filmRepository.findById(id).get());
        mav.addObject("cinemas", cinemaRepository.findAll());
        for (Session session : result){
            session.getSeats().sort((a,b) -> Math.toIntExact(b.getId() - a.getId()));
        }
        mav.addObject("sessions", result);
        return mav;
    }

    @PostMapping("/film/get/buy")
    public ModelAndView buyFilm(@RequestParam(required = true) Long id, @RequestParam Long sessionId, @RequestParam(required = false) List<Long> seats, @RequestParam Long buyerId){


        if (seats!=null) {
            logger.info(seats.toString());
            logger.info(String.valueOf(buyerId));
            logger.info(String.valueOf(sessionId));
            Long totalCost = 0L;
            for (Long seat : seats) {
                totalCost += seatRepository.findById(seat).get().getCost();
            }
            if (userRepository.findById(buyerId).get().getMoney() >= totalCost) {
                userRepository.findById(buyerId).get().setMoney(userRepository.findById(buyerId).get().getMoney() - totalCost);
                userRepository.save(userRepository.findById(buyerId).get());
                for (Long seat : seats) {
                    seatRepository.findById(seat).get().setBought(true);
                    seatRepository.save(seatRepository.findById(seat).get());
                }
            }
        }

        ModelAndView mav = new ModelAndView("getFilm");
        mav.addObject("getFilm", filmRepository.findById(id).get());
        mav.addObject("cinemas", cinemaRepository.findAll());
        List<Session> sessions = ((List<Session>) sessionRepository.findAll()).stream()
                .filter(c -> c.getFilm().getId() == id)
                .collect(Collectors.toList());
        for (Session session : sessions){
            session.getSeats().sort((a,b) -> Math.toIntExact(b.getId() - a.getId()));
        }
        mav.addObject("sessions", sessions);
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
            prev.setDuration(film.getDuration());
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
