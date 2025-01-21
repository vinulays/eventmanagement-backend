package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Employeestatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeStatusDao extends JpaRepository<Employeestatus,Integer> {
}
