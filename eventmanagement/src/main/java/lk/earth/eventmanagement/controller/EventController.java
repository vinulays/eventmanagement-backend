package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.EventDao;
import lk.earth.eventmanagement.dao.PaymentDao;
import lk.earth.eventmanagement.entity.Employee;
import lk.earth.eventmanagement.entity.Event;
import lk.earth.eventmanagement.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventDao eventDao;

    @Autowired
    PaymentDao paymentDao;

    @GetMapping("/list")
    public ResponseEntity<List<Event>> get(@RequestParam HashMap<String, String> params) {

        String id = params.get("id");
        String eventType = params.get("type");
        String packageName = params.get("package");
        String hallName = params.get("hall");
        String customerName = params.get("customerName");
        String customerMobile = params.get("customerMobile");

        List<Event> events = eventDao.findAll();
        if (params.isEmpty())
            return new ResponseEntity<List<Event>>(events, HttpStatus.OK);

        Stream<Event> eventStream = events.stream();


        if (id != null) eventStream = eventStream.filter(e -> e.getId() == Integer.parseInt(id));
        if (eventType != null)
            eventStream = eventStream.filter(e -> e.getEventtype().getName().replaceAll(" ", "").contains(eventType));
        if (packageName != null)
            eventStream = eventStream.filter(e -> e.getPackageField().getName().replaceAll(" ", "").contains(packageName));
        if (hallName != null)
            eventStream = eventStream.filter(e -> e.getHall().getName().replaceAll(" ", "").contains(hallName));
        if (customerName != null)
            eventStream = eventStream.filter(e -> e.getCustomer().getName().toLowerCase().replaceAll(" ", "").equals(customerName.toLowerCase()));
        if (customerMobile != null)
            eventStream = eventStream.filter(e -> e.getCustomer().getMobile().equals(customerMobile));

        events = eventStream.collect(Collectors.toList());
        return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
    }

    @PostMapping
    public HashMap<String, String> create(@RequestBody Event event) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        if (eventDao.findByHallAndDoeventAndStarttimeAndEndtime(event.getHall(), event.getDoevent(), event.getStarttime(), event.getEndtime()) != null) {
            errors += "Existing event for the given hall, date and time";

        }

        if (errors.equals("")) {
            eventDao.save(event);

        } else errors = "Server validation errors : <br> " + errors;

        response.put("customer", String.valueOf(event.getCustomer().getName()));
        response.put("url", "/events/" + event.getId());
        response.put("errors", errors);

        return response;
    }


    @PutMapping
    public HashMap<String, String> update(@RequestBody Event event) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        Event event1 = eventDao.findByHallAndDoeventAndStarttimeAndEndtime(event.getHall(), event.getDoevent(), event.getStarttime(), event.getEndtime());
        if (event1 != null && !Objects.equals(event1.getId(), event.getId())) {
            errors += "Existing event for the given hall, date and time";

        }
        if (errors.equals("")) eventDao.save(event);
        else errors = "Server Validation Errors : <br> " + errors;

        response.put("customer", String.valueOf(event.getCustomer().getName()));
        response.put("url", "/events/" + event.getId());
        response.put("errors", errors);

        return response;


    }


    @DeleteMapping(path = "/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        String errors = "";
        HashMap<String, String> response = new HashMap<>();
        Optional<Event> eventOptional = eventDao.findById(id);

        if (eventOptional.isEmpty()) {
            errors += "Event not found!";
        } else {
            Payment payment = paymentDao.findByEvent(eventOptional.get());
            if (payment != null) {
                errors += "Can't delete. A payment has assigned to the event";
            }


        }
        if (errors.equals(""))
            eventDao.deleteById(id);
        else {
            errors = "Server Validation Errors : <br> " + errors;

        }
        response.put("id", String.valueOf(id));
        response.put("url", "/events/" + id);
        response.put("errors", errors);

        return response;

    }
}
