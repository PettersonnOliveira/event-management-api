package com.portfolio.Event.Management.DTOS;

import java.time.LocalDateTime;

public record EventUpdateDTO(String title,
                             String description,
                             String location,
                             LocalDateTime date,
                             Integer capacity) {
    public  EventUpdateDTO(String title, String description, String location, LocalDateTime date, Integer capacity) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.capacity = capacity;

    }
}
