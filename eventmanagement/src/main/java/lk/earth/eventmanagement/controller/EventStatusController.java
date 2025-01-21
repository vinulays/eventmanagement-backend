package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.EventStatusDao;
import lk.earth.eventmanagement.entity.Eventstatus;
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
@RequestMapping("eventStatus")
public class EventStatusController {

    @Autowired
    EventStatusDao eventStatusDao;

    @GetMapping("/list")
    public ResponseEntity<List<Eventstatus>> get(){
        return new ResponseEntity<List<Eventstatus>>(eventStatusDao.findAll(), HttpStatus.OK);
    }
}
