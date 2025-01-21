package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Item;
import lk.earth.eventmanagement.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDao extends JpaRepository<Item,Integer> {
    List<Item> findBySupplier(Supplier supplier);
    Item findByName(String name);
}
