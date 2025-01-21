package lk.earth.eventmanagement.dao;

import lk.earth.eventmanagement.entity.Customer;
import lk.earth.eventmanagement.entity.Employee;
import lk.earth.eventmanagement.entity.Event;
import lk.earth.eventmanagement.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface EventDao extends JpaRepository<Event, Integer> {
    Event findByHallAndDoeventAndStarttimeAndEndtime(Hall hall, LocalDate doevent, LocalTime starttime, LocalTime endtime);

    List<Event> findByEmployee(Employee employee);

    List<Event> findByHall(Hall hall);

    List<Event> findByCustomer(Customer customer);
}
