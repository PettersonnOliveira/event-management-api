package com.portfolio.Event.Management.DTOS;

import com.portfolio.Event.Management.Entities.Event;

import java.time.LocalDateTime;

public record EventResponseDTO(
    Long id,
    String title,
    String description,
    String location,
    LocalDateTime date,
    Integer capacity
) {
    public EventResponseDTO(Event event) {
        this(event.getId(), event.getTitle(), event.getDescription(), event.getLocation(), event.getDate(), event.getCapacity());
    }
}
