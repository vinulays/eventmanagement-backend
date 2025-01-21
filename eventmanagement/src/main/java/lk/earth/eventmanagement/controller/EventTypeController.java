package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.EventTypeDao;
import lk.earth.eventmanagement.entity.Eventtype;
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
@RequestMapping("/types")
public class EventTypeController {

    @Autowired
    EventTypeDao eventTypeDao;

    @GetMapping("/list")
    public ResponseEntity<List<Eventtype>> get() {
        return new ResponseEntity<List<Eventtype>>(eventTypeDao.findAll(), HttpStatus.OK);
    }
}
