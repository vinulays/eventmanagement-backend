package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.PackageDao;
import lk.earth.eventmanagement.entity.Package;
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
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    PackageDao packageDao;

    @GetMapping("/list")
    public ResponseEntity<List<Package>> get() {

        return new ResponseEntity<List<Package>>(packageDao.findAll(), HttpStatus.OK);
    }
}
