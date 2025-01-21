package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee,Integer> {

    Employee findByNumber(String number);
    Employee findByNic(String nic);
}
