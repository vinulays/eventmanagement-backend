package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDao extends JpaRepository<Menu, Integer> {
    Menu findByName(String name);
}
