package com.example.demo.Controllers;

import com.example.demo.Entity.Film;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.FilmRepository;
import com.example.demo.Repository.StaffRepository;
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
public class StaffController {


    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private StaffRepository staffRepository;
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StaffController.class);
    @GetMapping("/staff/add")
    public ModelAndView addStaffGet(Staff staff, @RequestParam(required = false) Long id){
        logger.info("starting GET staff add");
        ModelAndView mav = new ModelAndView("addStaff");
        if (id!=null && staffRepository.findById(id).isPresent()){
            logger.info("found id");
            logger.info(staffRepository.findById(id).get().toString());
            mav.addObject("getStaff", staffRepository.findById(id).get());
        }
        return mav;
    }

    @PostMapping("/staff/add")
    public RedirectView addStaffPost(@Valid Staff staff){
        logger.info("post started");
        logger.info(staff.toString());
        if (staff.getId()!=null){
            Staff prev = staffRepository.findById(staff.getId()).get();
            prev.setId(staff.getId());
            prev.setBirth(staff.getBirth());
            prev.setFilms(staff.getFilms());
            prev.setLastName(staff.getLastName());
            prev.setAllInfo(staff.getAllInfo());
            prev.setFirstName(staff.getFirstName());
            prev.setMainInfo(staff.getMainInfo());
            staffRepository.save(prev);
        }
        else {
            logger.info("adding staff");
            if(staffRepository.findAllByFullName(staff.getFirstName(),staff.getLastName()).size()==0){
                logger.info("new full name!");
                staffRepository.save(staff);
            }
        }
        return new RedirectView("all");
    }

    @GetMapping("staff/del")
    public RedirectView delStaffGet(@RequestParam(required = true) Long id){
        List<Film> films = (List<Film>) filmRepository.findAll();
        for(Film film: films){
            if (film.getStaffs()!=null && film.getStaffs().contains(staffRepository.findById(id).get())){
                film.removeStaff(staffRepository.findById(id).get());
                filmRepository.save(film);
            }
        }
        staffRepository.deleteById(id);
        return new RedirectView("all");
    }

    @GetMapping("/staff/all")
    public ModelAndView allStaff(){
        ModelAndView mav = new ModelAndView("allStaff");
        mav.addObject("staffs",staffRepository.findAll());
        return mav;
    }
}
