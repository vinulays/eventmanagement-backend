package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.EmployeeStatusDao;
import lk.earth.eventmanagement.entity.Employeestatus;
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
@RequestMapping("employeeStatus")
public class EmployeeStatusController {

    @Autowired
    EmployeeStatusDao employeeStatusDao;

    @GetMapping("/list")
    public ResponseEntity<List<Employeestatus>> get() {
        return new ResponseEntity<List<Employeestatus>>(employeeStatusDao.findAll(), HttpStatus.OK);
    }
}
