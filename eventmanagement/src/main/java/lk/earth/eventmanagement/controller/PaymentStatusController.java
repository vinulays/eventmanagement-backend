package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.PaymentStatusDao;
import lk.earth.eventmanagement.entity.PaymentStatus;
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
@RequestMapping("/paymentStatus")
public class PaymentStatusController {

    @Autowired
    PaymentStatusDao paymentStatusDao;

    @GetMapping("/list")
    public ResponseEntity<List<PaymentStatus>> get() {
        return new ResponseEntity<>(paymentStatusDao.findAll(), HttpStatus.OK);
    }
}
