package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusDao extends JpaRepository<PaymentStatus, Integer> {
}
