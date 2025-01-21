package lk.earth.eventmanagement.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "employee_id",nullable = false)
    private Employee employee;

    @Column(name = "startDate")
    private LocalDate salaryStartDate;

    @Column(name = "endDate")
    private LocalDate salaryEndDate;

    @Column(name = "amount")
    private double amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getSalaryStartDate() {
        return salaryStartDate;
    }

    public void setSalaryStartDate(LocalDate salaryStartDate) {
        this.salaryStartDate = salaryStartDate;
    }

    public LocalDate getSalaryEndDate() {
        return salaryEndDate;
    }

    public void setSalaryEndDate(LocalDate salaryEndDate) {
        this.salaryEndDate = salaryEndDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
