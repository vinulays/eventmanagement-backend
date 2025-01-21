package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseDao extends JpaRepository<Expense, Integer> {
}
