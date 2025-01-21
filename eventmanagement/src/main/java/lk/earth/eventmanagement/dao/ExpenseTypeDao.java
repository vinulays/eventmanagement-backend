package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTypeDao extends JpaRepository<ExpenseType, Integer> {
}
