package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.GenderDao;
import lk.earth.eventmanagement.entity.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/genders")
public class GenderController {

    @Autowired
    GenderDao genderDao;

    @GetMapping("/list")
    public ResponseEntity<List<Gender>> get() {
        return new ResponseEntity<List<Gender>>(genderDao.findAll(), HttpStatus.OK);
    }
}
