package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.ExpenseDao;
import lk.earth.eventmanagement.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    ExpenseDao expenseDao;

    @GetMapping("/list")
    public ResponseEntity<List<Expense>> get(@RequestParam HashMap<String, String> params) {
        String expenseType = params.get("type");
        String description = params.get("description");
        String supplierName = params.get("supplierName");
        String itemName = params.get("itemName");
        String expenseDate = params.get("date");
        String cost = params.get("cost");

        List<Expense> expenses = expenseDao.findAll();

        if (params.isEmpty())
            return new ResponseEntity<>(expenses, HttpStatus.OK);

        Stream<Expense> expenseStream = expenses.stream();

        if (expenseType != null)
            expenseStream = expenseStream.filter((e -> e.getExpenseType().getName().toLowerCase().replaceAll(" ", "").contains(expenseType.toLowerCase().replaceAll(" ", ""))));

        if (description != null)
            expenseStream = expenseStream.filter(e -> e.getDescription().toLowerCase().replaceAll(" ", "").contains(description.toLowerCase().replaceAll(" ", "")));

        if (supplierName != null)
            expenseStream = expenseStream.filter(e -> e.getSupplier().getFullname().toLowerCase().replaceAll(" ", "").contains(supplierName.toLowerCase().replaceAll(" ", "")));

        if (itemName != null)
            expenseStream = expenseStream.filter(e -> e.getItem().getName().toLowerCase().replaceAll(" ", "").contains(itemName.toLowerCase().replaceAll(" ", "")));

        if (expenseDate != null)
            expenseStream = expenseStream.filter(e -> e.getDate().toString().replaceAll(" ", "").contains(expenseDate.replaceAll(" ", "")));

        if (cost != null)
            expenseStream = expenseStream.filter(e -> e.getCost().toString().replaceAll(" ", "").contains(cost.replaceAll(" ", "")));

        expenses = expenseStream.collect(Collectors.toList());

        return new ResponseEntity<>(expenses, HttpStatus.OK);

    }

    @PostMapping
    public HashMap<String, String> create(@RequestBody Expense expense) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";


        expenseDao.save(expense);

        response.put("Expense Date", expense.getDate().toString());
        response.put("url", "/expenses/" + expense.getId());
        response.put("errors", errors);

        return response;

    }

    @PutMapping
    public HashMap<String, String> update(@RequestBody Expense expense) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        expenseDao.save(expense);

        response.put("Expense Date", expense.getDate().toString());
        response.put("url", "/expenses/" + expense.getId());
        response.put("errors", errors);

        return response;
    }

    @DeleteMapping(path = "/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        String errors = "";
        HashMap<String, String> response = new HashMap<>();

        expenseDao.deleteById(id);

        response.put("id", String.valueOf(id));
        response.put("url", "/expenses/" + id);
        response.put("errors", errors);

        return response;
    }
}
