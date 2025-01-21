package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.SalaryDao;
import lk.earth.eventmanagement.entity.Salary;
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
@RequestMapping("/salaries")
public class SalaryController {

    @Autowired
    SalaryDao salaryDao;

    @GetMapping("/list")
    public ResponseEntity<List<Salary>> get(@RequestParam HashMap<String, String> params) {
        String employeeName = params.get("employeeName");
        String designation = params.get("designation");
        String employeeNumber = params.get("employeeNumber");
        String employeeNIC = params.get("employeeNIC");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");
        String amount = params.get("amount");

        List<Salary> salaries = salaryDao.findAll();

        if (params.isEmpty()) {
            return new ResponseEntity<>(salaries, HttpStatus.OK);
        }

        Stream<Salary> salaryStream = salaries.stream();

        if (employeeName != null)
            salaryStream = salaryStream.filter(s -> s.getEmployee().getFullname().toLowerCase().replaceAll(" ", "").contains(employeeName.toLowerCase().replaceAll(" ", "")));

        if (designation != null)
            salaryStream = salaryStream.filter(s -> s.getEmployee().getDesignation().getName().toLowerCase().replaceAll(" ", "").contains(designation.toLowerCase().replaceAll(" ", "")));

        if (employeeNumber != null)
            salaryStream = salaryStream.filter(s -> s.getEmployee().getNumber().replaceAll(" ", "").contains(employeeNumber.replaceAll(" ", "")));

        if (employeeNIC != null)
            salaryStream = salaryStream.filter(s -> s.getEmployee().getNic().replaceAll(" ", "").contains(employeeNIC.replaceAll(" ", "")));

        if (startDate != null)
            salaryStream = salaryStream.filter(s -> s.getSalaryStartDate().toString().replaceAll(" ", "").contains(startDate.replaceAll(" ", "")));

        if (endDate != null)
            salaryStream = salaryStream.filter(s -> s.getSalaryEndDate().toString().replaceAll(" ", "").contains(endDate.replaceAll(" ", "")));

        if (amount != null)
            salaryStream = salaryStream.filter(s -> String.valueOf(s.getAmount()).replaceAll(" ", "").contains(amount.replaceAll(" ", "")));

        salaries = salaryStream.collect(Collectors.toList());

        return new ResponseEntity<>(salaries, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@RequestBody Salary salary) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        if (salaryDao.findByEmployeeAndSalaryStartDateAndSalaryEndDate(salary.getEmployee(), salary.getSalaryStartDate(), salary.getSalaryEndDate()) != null) {
            errors = errors + "<br> Salary for given employee for given month already exists";
        }

        if (errors.equals("")) {
            salaryDao.save(salary);
        } else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(salary.getId()));
        response.put("url", "/salaries/" + salary.getId());
        response.put("errors", errors);

        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> update(@RequestBody Salary salary) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        Salary salary1 = salaryDao.findByEmployeeAndSalaryStartDateAndSalaryEndDate(salary.getEmployee(), salary.getSalaryStartDate(), salary.getSalaryEndDate());

        if (salary1 != null && !Objects.equals(salary.getId(), salary1.getId())) {
            errors += "<br> Existing salary for same employee in same month";
        }

        if (errors.equals("")) salaryDao.save(salary);
        else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(salary.getId()));
        response.put("url", "/salaries/" + salary.getId());
        response.put("errors", errors);

        return response;
    }

    @DeleteMapping(path = "/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        Optional<Salary> salaryOptional = salaryDao.findById(id);

        if (salaryOptional.isEmpty()) {
            errors += "Salary not found";
        }

        if (errors.equals("")) {
            salaryDao.deleteById(id);
        } else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(id));
        response.put("url", "/salaries/" + id);
        response.put("errors", errors);

        return response;
    }
}
