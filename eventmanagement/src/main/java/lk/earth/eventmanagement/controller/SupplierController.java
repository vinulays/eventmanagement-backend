package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.ItemDao;
import lk.earth.eventmanagement.dao.SupplierDao;
import lk.earth.eventmanagement.entity.Employee;
import lk.earth.eventmanagement.entity.Item;
import lk.earth.eventmanagement.entity.Supplier;
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
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    SupplierDao supplierDao;

    @Autowired
    ItemDao itemDao;

    @GetMapping("/list")
    public ResponseEntity<List<Supplier>> get(@RequestParam HashMap<String, String> params) {
        String fullname = params.get("fullname");
        String email = params.get("email");
        String nic = params.get("nic");
        String accountNo = params.get("accountNo");
        List<Supplier> suppliers = supplierDao.findAll();

        if (params.isEmpty()) {
            return new ResponseEntity<List<Supplier>>(suppliers, HttpStatus.OK);

        }

        Stream<Supplier> supplierStream = suppliers.stream();

        if (fullname != null)
            supplierStream = supplierStream.filter(e -> e.getFullname().replaceAll(" ", "").toLowerCase().contains(fullname.toLowerCase()));
        if (email != null)
            supplierStream = supplierStream.filter(e -> e.getEmail().replaceAll(" ", "").toLowerCase().contains(email.toLowerCase()));
        if (nic != null)
            supplierStream = supplierStream.filter(e -> e.getNic().replaceAll(" ", "").contains(nic));
        if (accountNo != null)
            supplierStream = supplierStream.filter(e -> e.getAccountNo().replaceAll(" ", "").contains(accountNo));

        suppliers = supplierStream.collect(Collectors.toList());

        return new ResponseEntity<List<Supplier>>(suppliers, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@RequestBody Supplier supplier) {
        HashMap<String, String> response = new HashMap<>();

        String errors = "";

        if (supplierDao.findByNic(supplier.getNic()) != null) {
            errors = errors + "<br> Existing NIC";
        }

        if (supplierDao.findByEmail(supplier.getEmail()) != null) {
            errors = errors + "<br> Existing Email";
        }

        if (supplierDao.findByAccountNo(supplier.getAccountNo()) != null) {
            errors = errors + "<br> Existing Account Number";
        }

        if (errors.equals("")) {
            supplierDao.save(supplier);
        } else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(supplier.getId()));
        response.put("url", "/suppliers/" + supplier.getId());
        response.put("errors", errors);

        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> update(@RequestBody Supplier supplier) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        Supplier supplier1 = supplierDao.findByNic(supplier.getNic());
        Supplier supplier2 = supplierDao.findByEmail(supplier.getEmail());
        Supplier supplier3 = supplierDao.findByAccountNo(supplier.getAccountNo());

        if (supplier1 != null && !Objects.equals(supplier.getId(), supplier1.getId())) {
            errors += "<br> Existing NIC";
        }

        if (supplier2 != null && !Objects.equals(supplier.getId(), supplier1.getId())) {
            errors += "<br> Existing Email";
        }

        if (supplier3 != null && !Objects.equals(supplier.getId(), supplier1.getId())) {
            errors += "<br> Existing Account Number";
        }

        if (errors.equals("")) supplierDao.save(supplier);
        else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(supplier.getId()));
        response.put("url", "/suppliers/" + supplier.getId());
        response.put("errors", errors);

        return response;
    }

    @DeleteMapping(path = "/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        String errors = "";
        HashMap<String, String> response = new HashMap<>();
        Optional<Supplier> supplierOptional = supplierDao.findById(id);

        if (supplierOptional.isEmpty()) {
            errors += "Supplier not found!";
        } else {
            List<Item> items = itemDao.findBySupplier(supplierOptional.get());

            if (items.size() > 0) {
                errors += "Can't delete. Supplier is assigned to one or more items";
            }
        }

        if (errors.equals("")) {
            supplierDao.deleteById(id);
        } else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(id));
        response.put("url", "/suppliers/" + id);
        response.put("errors", errors);

        return response;

    }
}
