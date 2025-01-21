package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.CustomerDao;
import lk.earth.eventmanagement.dao.EventDao;
import lk.earth.eventmanagement.entity.Customer;
import lk.earth.eventmanagement.entity.Employee;
import lk.earth.eventmanagement.entity.Event;
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
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerDao customerDao;

    @Autowired
    EventDao eventDao;

    @GetMapping(path = "/list")
    public ResponseEntity<List<Customer>> get(@RequestParam HashMap<String, String> params) {

        String name = params.get("name");
        String gender = params.get("gender");
        String address = params.get("address");
        String mobile = params.get("mobile");
        String land = params.get("land");

        List<Customer> customers = customerDao.findAll();

        if (params.isEmpty())
            return new ResponseEntity<>(customers, HttpStatus.OK);

        Stream<Customer> customerStream = customers.stream();

        if (name != null)
            customerStream = customerStream.filter(c -> c.getName().toLowerCase().replaceAll(" ", "").contains(name.toLowerCase()));
        if (gender != null)
            customerStream = customerStream.filter(c -> c.getGender().getName().toLowerCase().replaceAll(" ", "").equals(gender.toLowerCase()));
        if (address != null)
            customerStream = customerStream.filter(c -> c.getAddress().toLowerCase().replaceAll(" ", "").contains(address.toLowerCase()));
        if (mobile != null)
            customerStream = customerStream.filter(c -> c.getMobile().replaceAll(" ", "").contains(mobile));
        if (land != null)
            customerStream = customerStream.filter(c -> c.getLand().replaceAll(" ", "").contains(land));

        customers = customerStream.collect(Collectors.toList());
        return new ResponseEntity<>(customers, HttpStatus.OK);

    }

    @PostMapping
    public HashMap<String, String> add(@RequestBody Customer customer) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        if (customerDao.findByMobile(customer.getMobile()) != null)
            errors = errors + "<br> Existing mobile number";

        if (customerDao.findByLand(customer.getLand()) != null)
            errors = errors + "<br> Existing landline number";

        if (errors.equals("")) {
            customerDao.save(customer);
        } else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(customer.getId()));
        response.put("url", "/employees/" + customer.getId());
        response.put("errors", errors);

        return response;

    }

    @PutMapping
    public HashMap<String, String> update(@RequestBody Customer customer) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        Customer customer1 = customerDao.findByMobile(customer.getMobile());
        Customer customer2 = customerDao.findByLand(customer.getLand());

        if (customer1 != null && !Objects.equals(customer.getId(), customer1.getId())) {
            errors += "<br> Existing mobile number";
        }

        if (customer2 != null && !Objects.equals(customer.getId(), customer2.getId())) {
            errors += "<br> Existing landline number";
        }

        if (errors.equals("")) customerDao.save(customer);
        else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(customer.getId()));
        response.put("url", "/employees/" + customer.getId());
        response.put("errors", errors);

        return response;

    }

    @DeleteMapping("/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        String errors = "";
        HashMap<String, String> response = new HashMap<>();
        Optional<Customer> customerOptional = customerDao.findById(id);

        if (customerOptional.isEmpty()) {
            errors += "Customer not found!";
        } else {
            List<Event> events = eventDao.findByCustomer(customerOptional.get());

            if (events.size() > 0) {
                errors += "Can't delete. Customer has booked an event!";
            }

        }
        if (errors.equals(""))
            customerDao.deleteById(id);
        else {
            errors = "Server Validation Errors : <br> " + errors;

        }
        response.put("id", String.valueOf(id));
        response.put("url", "/customer/" + id);
        response.put("errors", errors);

        return response;

    }
}
