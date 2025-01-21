package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.ExpenseTypeDao;
import lk.earth.eventmanagement.entity.ExpenseType;
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
@RequestMapping("/expenseTypes")
public class ExpenseTypeController {

    @Autowired
    ExpenseTypeDao expenseTypeDao;

    @GetMapping("/list")
    public ResponseEntity<List<ExpenseType>> get(){
        return new ResponseEntity<>(expenseTypeDao.findAll(), HttpStatus.OK);
    }
}
