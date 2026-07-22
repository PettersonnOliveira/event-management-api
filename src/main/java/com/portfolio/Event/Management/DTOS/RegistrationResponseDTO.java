package com.portfolio.Event.Management.DTOS;

import com.portfolio.Event.Management.Entities.Registration;

import java.time.LocalDateTime;

public record RegistrationResponseDTO(
        Long id,
        LocalDateTime registrationDate,
        UserResponseDTO user,
        EventResponseDTO event
) {

    public RegistrationResponseDTO(Registration registration) {
        this(
                registration.getId(),
                registration.getRegistrationDate(),
                new UserResponseDTO(registration.getUser()),
                new EventResponseDTO(registration.getEvent())
        );
    }
}
