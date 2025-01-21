package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.MenuDao;
import lk.earth.eventmanagement.entity.Item;
import lk.earth.eventmanagement.entity.Menu;
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
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    MenuDao menuDao;

    @GetMapping("/list")
    public ResponseEntity<List<Menu>> get(@RequestParam HashMap<String, String> params) {
        String name = params.get("name");
        String price = params.get("price");

        List<Menu> menus = menuDao.findAll();

        if (params.isEmpty()) {
            return new ResponseEntity<>(menuDao.findAll(), HttpStatus.OK);

        }

        Stream<Menu> menuStream = menus.stream();

        if (name != null)
            menuStream = menuStream.filter(e -> e.getName().toLowerCase().replaceAll(" ", "").contains(name));

        if (price != null)
            menuStream = menuStream.filter(e -> e.getPrice().toString().contains(price));

        menus = menuStream.collect(Collectors.toList());

        return new ResponseEntity<>(menus, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getByID(@PathVariable("id") Integer id) {
        Optional<Menu> menuOptional = menuDao.findById(id);

        if (menuOptional.isPresent()) {
            return new ResponseEntity<Menu>(menuOptional.get(), HttpStatus.OK);

        }else{
            return null;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@RequestBody Menu menu) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

//        if (menuDao.findByName(menu.getName()) != null) {
//            errors = errors + "<br> Existing Menu Name";
//        }

        menuDao.save(menu);

        response.put("id", String.valueOf(menu.getId()));
        response.put("url", "/menus/" + menu.getId());
        response.put("errors", errors);

        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> update(@RequestBody Menu menu) {
        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        Menu menu1 = menuDao.findByName(menu.getName());

//        if (menu1 != null && !Objects.equals(menu.getId(), menu1.getId())) {
//            errors += "<br> Existing menu name";
//        }

        menuDao.save(menu);

        response.put("id", String.valueOf(menu.getId()));
        response.put("url", "/menus/" + menu.getId());
        response.put("errors", errors);

        return response;
    }

    @DeleteMapping(path = "/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        String errors = "";
        HashMap<String, String> response = new HashMap<>();
        Optional<Menu> menuOptional = menuDao.findById(id);

        if (menuOptional.isEmpty()) {
            errors += "Menu not found";
        }

        if (errors.equals("")) {
            menuDao.deleteById(id);
        } else {
            errors = "Server validation errors : <br> " + errors;
        }

        response.put("id", String.valueOf(id));
        response.put("url", "/menus/" + id);
        response.put("errors", errors);

        return response;
    }
}
