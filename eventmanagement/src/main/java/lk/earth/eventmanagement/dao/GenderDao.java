package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderDao extends JpaRepository<Gender,Integer> {
}
