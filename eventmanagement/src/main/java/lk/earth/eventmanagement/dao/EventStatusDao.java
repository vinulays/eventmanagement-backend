package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Eventstatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusDao extends JpaRepository<Eventstatus,Integer> {
}
