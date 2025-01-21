package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.DesignationDao;
import lk.earth.eventmanagement.entity.Designation;
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
@RequestMapping("/designations")
public class DesignationController {

    @Autowired
    DesignationDao designationDao;

    @GetMapping("/list")
    public ResponseEntity<List<Designation>> get() {
        return new ResponseEntity<List<Designation>>(designationDao.findAll(), HttpStatus.OK);
    }
}
