package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

    Customer findByName(String name);

    Customer findByMobile(String mobile);
    Customer findByLand(String land);
}
