package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageDao extends JpaRepository<Package,Integer> {
}
