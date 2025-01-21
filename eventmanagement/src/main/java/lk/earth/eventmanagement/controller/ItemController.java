package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.ItemDao;
import lk.earth.eventmanagement.entity.Employee;
import lk.earth.eventmanagement.entity.Item;
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
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemDao itemDao;

    @GetMapping("/list")
    public ResponseEntity<List<Item>> get(@RequestParam HashMap<String, String> params) {
        String name = params.get("name");
        String price = params.get("price");
        String quantity = params.get("quantity");
        String supplier = params.get("supplier");


        List<Item> items = itemDao.findAll();

        if (params.isEmpty()) {
            return new ResponseEntity<>(items, HttpStatus.OK);
        }

        Stream<Item> itemStream = items.stream();

        if (name != null)
            itemStream = itemStream.filter(e -> e.getName().toLowerCase().replaceAll(" ", "").contains(name.toLowerCase()));
        if (price != null)
            itemStream = itemStream.filter(e -> String.valueOf(e.getPrice()).contains(price));
        if (quantity != null)
            itemStream = itemStream.filter(e -> String.valueOf(e.getQuantity()).contains(quantity));
        if (supplier != null) {
            itemStream = itemStream.filter(e -> Objects.equals(e.getSupplier().getFullname().replaceAll(" ", ""), supplier));


        }
        items = itemStream.collect(Collectors.toList());

        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@RequestBody Item item) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        if (itemDao.findByName(item.getName()) != null) {
            errors = errors + "<br> Existing Item Name";
        }

        if (errors.equals("")) {
            itemDao.save(item);
        } else errors = "Server validation errors  : <br> " + errors;

        response.put("id", String.valueOf(item.getId()));
        response.put("url", "/items/" + item.getId());
        response.put("errors", errors);

        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> update(@RequestBody Item item) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        Item item1 = itemDao.findByName(item.getName());

        if (item1 != null && !Objects.equals(item.getId(), item1.getId())) {
            errors += "<br> Existing item name";
        }

        if (errors.equals("")) itemDao.save(item);
        else errors = "Server validation errors : <br> " + errors;

        response.put("id", String.valueOf(item.getId()));
        response.put("url", "/employees/" + item.getId());
        response.put("errors", errors);

        return response;
    }

    @DeleteMapping(path = "/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        String errors = "";
        HashMap<String, String> response = new HashMap<>();
        Optional<Item> itemOptional = itemDao.findById(id);

        if (itemOptional.isEmpty()) {
            errors += "Item not found";
        }

        if (errors.equals("")) {
            itemDao.deleteById(id);
        } else {
            errors = "Server validation errors : <br> " + errors;
        }

        response.put("id", String.valueOf(id));
        response.put("url", "/items/" + id);
        response.put("errors", errors);

        return response;
    }
}
