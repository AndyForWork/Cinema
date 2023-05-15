package com.example.demo.Controllers;

import com.example.demo.Entity.Film;
import com.example.demo.Entity.Seat;
import com.example.demo.Entity.Session;
import com.example.demo.Repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);

    @GetMapping("/session/add")
    private ModelAndView addSessionGet(Session session, @RequestParam(required = false) Long id){
        ModelAndView mav = new ModelAndView("addSession");
        mav.addObject("films",filmRepository.findAll());
        mav.addObject("cinemas",cinemaRepository.findAll());
        mav.addObject("halls", filmRepository.findAll());
        if (id!=null && sessionRepository.findById(id).isPresent())
            mav.addObject("getSession", sessionRepository.findById(id).get());
        return mav;
    }

    @PostMapping("/session/add")
    private ModelAndView addSessionPost(@Valid Session session, @Valid Long baseCost){
        logger.info(String.valueOf(session));
        List<Session> sessions = (List<Session>) sessionRepository.findAll();
        sessions.sort((a,b) -> Math.toIntExact(a.getStartTime().getTime() - b.getStartTime().getTime()));
        boolean collision = false;

        for (int i=0;i<sessions.size();i++){
            if (sessions.get(i).getId() == session.getId())
                continue;
            if (sessions.get(i).getHall().getId()== session.getHall().getId()){
                if (Math.max(sessions.get(i).getStartTime().getTime(),session.getStartTime().getTime())<
                        Math.min(sessions.get(i).getStartTime().getTime()+sessions.get(i).getFilm().getDuration().getTime(),session.getStartTime().getTime()+session.getFilm().getDuration().getTime()))    //проверка коллизии
                    collision = true;
            }
        }

        if (!collision){
            for (int p=0;p<session.getHall().getRows()*session.getHall().getSeatsInRow(); p++){
                Seat seat = new Seat(false, baseCost);
                session.getSeats().add(seat);
                seat.setSession(session);
            }
            logger.info(String.valueOf(session));
            sessionRepository.save(session);
            seatRepository.saveAll(session.getSeats());
        }

        //нужно добавить поле, которое бы говорило смогли ли мы добавить фильм

        ModelAndView mav = new ModelAndView("addSession");
        mav.addObject("films",filmRepository.findAll());
        mav.addObject("cinemas",cinemaRepository.findAll());
        mav.addObject("halls", filmRepository.findAll());
        return mav;
    }

    @GetMapping("/session/all")
    private ModelAndView allSessionGet(){
        ModelAndView mav = new ModelAndView("allSession");

        mav.addObject("films",filmRepository.findAll());
        mav.addObject("cinemas",cinemaRepository.findAll());
        mav.addObject("halls", filmRepository.findAll());
        mav.addObject("sessions", sessionRepository.findAll());
        return mav;
    }

    @GetMapping("/search")
    private ModelAndView testGet(){
        ModelAndView mav = new ModelAndView("Search");
        mav.addObject("films",filmRepository.findAll());
        mav.addObject("cinemas", cinemaRepository.findAll());
        return mav;
    }

    @PostMapping("/search")
    private ModelAndView testPost(@Valid String filmName, @RequestParam(name="halls", required = false) ArrayList<Long> halls, @Valid Long minPrice, @Valid Long maxPrice, @Valid String dateOfSession){
        logger.info("ok");
        logger.info(filmName);
        logger.info(String.valueOf(halls));
        logger.info(String.valueOf(minPrice));
        logger.info(String.valueOf(maxPrice));
        logger.info(dateOfSession);
        List<Session> res = (List<Session>) sessionRepository.findAll();
        List<Film> result =  res.stream()
                //.filter( c -> Pattern.compile(".*" + filmName + ".*").matcher(c.getFilm().getName()).find())
                .filter( c -> (minPrice != null && maxPrice != null) ? c.minCost()>=minPrice && c.maxCost()<=maxPrice : true)
                .filter( c -> (halls != null) ? halls.contains(c.getHall().getId()) : true)
                .filter( c->  {
                    try {
                        return dateOfSession == "" || ((dateOfSession != "" && dateOfSession!=null) && c.getStartTime().getDay() == ((DateFormat) new SimpleDateFormat("yyyy-MM-dd")).parse(dateOfSession).getDay());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map( c -> c.getFilm())
                .collect(Collectors.toList());


        ModelAndView mav = new ModelAndView("Search");
        mav.addObject("films", result);
        mav.addObject("cinemas", cinemaRepository.findAll());

        return mav;
    }


}
