package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierDao extends JpaRepository<Supplier,Integer> {

    Supplier findByNic(String nic);
    Supplier findByEmail(String email);
    Supplier findByAccountNo(String accountno);
}
