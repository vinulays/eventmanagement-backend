package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Eventtype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeDao extends JpaRepository<Eventtype,Integer> {
}
