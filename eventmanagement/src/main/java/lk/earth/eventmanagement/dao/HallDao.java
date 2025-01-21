package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallDao extends JpaRepository<Hall,Integer> {
    Hall findByName(String name);

}
