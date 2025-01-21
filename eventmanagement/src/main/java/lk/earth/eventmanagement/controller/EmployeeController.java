package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.EmployeeDao;
import lk.earth.eventmanagement.dao.EventDao;
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
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    EventDao eventDao;

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> get(@RequestParam HashMap<String, String> params) {
        String number = params.get("number");
        String callingName = params.get("callingname");
        String gender = params.get("gender");
        String designation = params.get("designation");
        String fullName = params.get("fullname");

        List<Employee> employees = employeeDao.findAll();
        if (params.isEmpty())
            return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);

        Stream<Employee> eventStream = employees.stream();


        if (number != null) eventStream = eventStream.filter(e -> e.getNumber().contains(number));
        if (callingName != null)
            eventStream = eventStream.filter(e -> e.getCallingname().replaceAll(" ", "").contains(callingName));
        if (gender != null)
            eventStream = eventStream.filter(e -> e.getGender().getName().replaceAll(" ", "").equals(gender));
        if (designation != null)
            eventStream = eventStream.filter(e -> e.getDesignation().getName().replaceAll(" ", "").equals(designation));

        if (fullName != null)
            eventStream = eventStream.filter(e -> e.getFullname().replaceAll(" ", "").contains(fullName));
        employees = eventStream.collect(Collectors.toList());

        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@RequestBody Employee employee) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        if (employeeDao.findByNumber(employee.getNumber()) != null)
            errors = errors + "<br> Existing Number";

        if (employeeDao.findByNic(employee.getNic()) != null)
            errors = errors + "<br> Existing NIC";

        if (errors.equals("")) {
            employeeDao.save(employee);

        } else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(employee.getId()));
        response.put("url", "/employees/" + employee.getId());
        response.put("errors", errors);

        return response;

    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> update(@RequestBody Employee employee) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";


        Employee employee1 = employeeDao.findByNumber(employee.getNumber());
        Employee employee2 = employeeDao.findByNic(employee.getNic());

        if (employee1 != null && !Objects.equals(employee.getId(), employee1.getId())) {
            errors += "<br> Existing number";
        }

        if (employee2 != null && !Objects.equals(employee.getId(), employee2.getId())) {
            errors += "<br> Existing NIC";
        }

        if (errors.equals("")) employeeDao.save(employee);
        else errors = "Server Validation Errors : <br> " + errors;

        response.put("id", String.valueOf(employee.getId()));
        response.put("url", "/employees/" + employee.getId());
        response.put("errors", errors);

        return response;


    }

    @DeleteMapping(path = "/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        String errors = "";
        HashMap<String, String> response = new HashMap<>();
        Optional<Employee> employeeOptional = employeeDao.findById(id);

        if (employeeOptional.isEmpty()) {
            errors += "Employee not found!";
        } else {
            List<Event> events = eventDao.findByEmployee(employeeOptional.get());

            if(events.size() > 0){
                errors += "Can't delete. Employee is assigned to an event!";
            }

        }
        if (errors.equals(""))
            employeeDao.deleteById(id);
        else {
            errors = "Server Validation Errors : <br> " + errors;

        }
        response.put("id", String.valueOf(id));
        response.put("url", "/employees/" + id);
        response.put("errors", errors);

        return response;

    }


}
