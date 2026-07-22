package com.portfolio.Event.Management.Repositories;

import com.portfolio.Event.Management.Entities.Event;
import com.portfolio.Event.Management.Entities.Registration;
import com.portfolio.Event.Management.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByUserAndEvent(User user, Event event);

    long countByEvent(Event event);

    List<Registration> findByEvent(Event event);

}
