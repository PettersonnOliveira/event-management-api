package com.portfolio.Event.Management.Repositories;

import com.portfolio.Event.Management.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Long id(Long id);
}
