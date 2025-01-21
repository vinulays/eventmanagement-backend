package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationDao extends JpaRepository<Designation,Integer> {
}
