package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.entity.*;
import lk.earth.eventmanagement.util.RegexProvider;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/regexes")
public class RegexController {

    @GetMapping("/employee")
    public HashMap<String, HashMap<String, String>> employee() {
        return RegexProvider.get(new Employee());
    }

    @GetMapping("/event")
    public HashMap<String, HashMap<String, String>> event() {
        return RegexProvider.get(new Event());
    }

    @GetMapping("/hall")
    public HashMap<String, HashMap<String, String>> hall() {
        return RegexProvider.get(new Hall());
    }

    @GetMapping("/supplier")
    public HashMap<String, HashMap<String, String>> supplier() {
        return RegexProvider.get(new Supplier());
    }

    @GetMapping("/item")
    public HashMap<String, HashMap<String, String>> item() {
        return RegexProvider.get(new Item());
    }

    @GetMapping("/customer")
    public HashMap<String, HashMap<String, String>> customer() {
        return RegexProvider.get(new Customer());
    }

    @GetMapping("/payment")
    public HashMap<String, HashMap<String, String>> payment() {
        return RegexProvider.get(new Payment());
    }

    @GetMapping("/submenu")
    public HashMap<String, HashMap<String, String>> submenu() {
        return RegexProvider.get(new SubMenu());
    }

    @GetMapping("/menu")
    public HashMap<String, HashMap<String, String>> menu() {
        return RegexProvider.get(new Menu());
    }

    @GetMapping("/expense")
    public HashMap<String, HashMap<String, String>> expense() {
        return RegexProvider.get(new Expense());
    }

    @GetMapping("/salary")
    public HashMap<String, HashMap<String, String>> salary() {
        return RegexProvider.get(new Salary());
    }


}
