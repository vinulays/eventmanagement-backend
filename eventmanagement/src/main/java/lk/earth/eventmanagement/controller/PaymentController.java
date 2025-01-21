package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.PaymentDao;
import lk.earth.eventmanagement.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentDao paymentDao;

    @GetMapping("/list")
    public ResponseEntity<List<Payment>> get(@RequestParam HashMap<String, String> params) {
        String customerName = params.get("customerName");
        String eventDate = params.get("eventDate");
        String eventType = params.get("eventType");
        String eventPackage = params.get("eventPackage");
        String eventCount = params.get("eventCount");
        String totalCost = params.get("totalCost");
        String paidAmount = params.get("paidAmount");
        String dueAmount = params.get("dueAmount");
        String status = params.get("status");

        List<Payment> payments = paymentDao.findAll();

        if (params.isEmpty())
            return new ResponseEntity<>(payments, HttpStatus.OK);

        Stream<Payment> paymentStream = payments.stream();

        if (customerName != null)
            paymentStream = paymentStream.filter(p -> p.getCustomer().getName().toLowerCase().replaceAll(" ", "").contains(customerName.toLowerCase().replaceAll(" ", "")));

        if (eventDate != null)
            paymentStream = paymentStream.filter(p -> p.getEvent().getDoevent().toString().replaceAll(" ", "").contains(eventDate.replaceAll(" ", "")));


        if (totalCost != null)
            paymentStream = paymentStream.filter(p -> p.getTotalCost().toString().contains(totalCost));

        if (paidAmount != null)
            paymentStream = paymentStream.filter(p -> p.getPaidAmount().toString().contains(paidAmount));

        if (dueAmount != null)
            paymentStream = paymentStream.filter(p -> p.getDueAmount().toString().contains(dueAmount));

        if (status != null)
            paymentStream = paymentStream.filter(p -> p.getStatus().getName().toLowerCase().replaceAll(" ", "").equals(status.toLowerCase().replaceAll(" ", "")));

        if (eventType != null)
            paymentStream = paymentStream.filter(p -> p.getEvent().getEventtype().getName().replaceAll(" ", "").equals(eventType.replaceAll(" ", "")));

        if (eventPackage != null)
            paymentStream = paymentStream.filter(p -> p.getEvent().getPackageField().getName().replaceAll(" ", "").equals(eventPackage.replaceAll(" ", "")));

        if (eventCount != null)
            paymentStream = paymentStream.filter(p -> p.getEvent().getCount().toString().equals(eventCount));

        payments = paymentStream.collect(Collectors.toList());

        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@RequestBody Payment payment) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        if (paymentDao.findByEvent(payment.getEvent()) != null)
            errors = errors + "<br> Existing payment for same event";

        if (errors.equals("")) {
            paymentDao.save(payment);
        } else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(payment.getId()));
        response.put("url", "/payments/" + payment.getId());
        response.put("errors", errors);

        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> update(@RequestBody Payment payment) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        Payment payment1 = paymentDao.findByEvent(payment.getEvent());

        if (payment1 != null && !Objects.equals(payment.getId(), payment1.getId())) {
            errors += "<br> Existing payment for same event";
        }
        if (errors.equals("")) paymentDao.save(payment);
        else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(payment.getId()));
        response.put("url", "/payments/" + payment.getId());
        response.put("errors", errors);

        return response;

    }

    @DeleteMapping(path = "{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        String errors = "";
        HashMap<String, String> response = new HashMap<>();
        Optional<Payment> paymentOptional = paymentDao.findById(id);

        if (paymentOptional.isEmpty()) {
            errors += "Payment not found!";
        } else {
            if (paymentOptional.get().getStatus().getName().equals("Not Completed")) {
                errors += "Can't delete. Payment has not completed";
            }
        }

        if (errors.equals(""))
            paymentDao.deleteById(id);
        else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(id));
        response.put("url", "/payments/" + id);
        response.put("errors", errors);

        return response;

    }
}
