package lk.earth.eventmanagement.controller;

import lk.earth.eventmanagement.dao.SubMenuDao;
import lk.earth.eventmanagement.entity.SubMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/submenus")
public class SubMenuController {

    @Autowired
    SubMenuDao subMenuDao;

    @DeleteMapping("/{id}")
    public HashMap<String, String> delete(@PathVariable("id") Integer id) {
        String errors = "";
        HashMap<String, String> response = new HashMap<>();
        Optional<SubMenu> subMenuOptional = subMenuDao.findById(id);

        if (subMenuOptional.isEmpty()) {
            errors += "Sub menu not found";
        }

        if (errors.equals("")) {
            subMenuDao.deleteById(id);
        } else {
            errors = "Server validation errors : <br> " + errors;

        }
        response.put("id", String.valueOf(id));
        response.put("url", "/items/" + id);
        response.put("errors", errors);

        return response;
    }
}
