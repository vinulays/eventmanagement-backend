package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Employee;
import lk.earth.eventmanagement.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SalaryDao extends JpaRepository<Salary,Integer> {

    Salary findByEmployeeAndSalaryStartDateAndSalaryEndDate(Employee employee, LocalDate salaryStartDate,LocalDate salaryEndDate);

}
