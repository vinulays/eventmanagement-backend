package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Event;
import lk.earth.eventmanagement.entity.Payment;
import lk.earth.eventmanagement.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentDao extends JpaRepository<Payment, Integer> {

    Payment findByEvent(Event event);
    List<Payment> findByStatus(PaymentStatus status);
}
