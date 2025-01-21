package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.EventDao;
import lk.earth.eventmanagement.dao.HallDao;
import lk.earth.eventmanagement.entity.Event;
import lk.earth.eventmanagement.entity.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("halls")
public class HallController {

    @Autowired
    HallDao hallDao;

    @Autowired
    EventDao eventDao;

    @GetMapping("/list")
    public ResponseEntity<List<Hall>> get() {
        return new ResponseEntity<List<Hall>>(hallDao.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Event>> getEvents(@PathVariable("id") Integer id) {
        Optional<Hall> hallOptional = hallDao.findById(id);

        if (hallOptional.isPresent()) {
            List<Event> events = eventDao.findByHall(hallOptional.get());
            return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Event>>(new ArrayList<Event>(0), HttpStatus.OK);
        }


    }

    @PostMapping
    public HashMap<String, String> create(@RequestBody Hall hall) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        if (hallDao.findByName(hall.getName()) != null) {
            errors += "Existing hall. Please try again";
        }

        if (errors.equals("")) {
            hallDao.save(hall);
        } else errors = "Server validation errors: <br> " + errors;

        response.put("Hall Name", hall.getName());
        response.put("errors", errors);


        return response;
    }

    @PutMapping
    public HashMap<String, String> update(@RequestBody Hall hall) {
        HashMap<String, String> response = new HashMap<>();

        String errors = "";
        Optional<Hall> hallOptional = hallDao.findById(hall.getId());
        if (hallOptional.isEmpty()) {
            errors += "Hall not found. Please try again";
        }

        if (errors.equals("")) {
            hallDao.save(hall);
        } else errors = "Server validation errors: <br> " + errors;

        response.put("Hall Name", hall.getName());
        response.put("errors", errors);

        return response;

    }

    @DeleteMapping("/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";
        Optional<Hall> hallOptional = hallDao.findById(id);

        if (hallOptional.isEmpty()) {
            errors += "Hall not found!";
        } else {
            List<Event> events = eventDao.findByHall(hallOptional.get());

            if (events.size() > 0) {
                errors += "Can't delete. Hall is currently assigned to one or more events!";
            }
        }

        if (errors.equals("")) {
            hallDao.deleteById(id);
        } else {
            errors = "Server Validation Errors : <br>" + errors;
        }

        response.put("id", String.valueOf(id));
        response.put("url", "/halls/" + id);
        response.put("errors", errors);

        return response;

    }
}
