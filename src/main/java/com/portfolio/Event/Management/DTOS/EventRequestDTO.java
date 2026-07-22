package com.portfolio.Event.Management.DTOS;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record EventRequestDTO(
       @NotBlank String title,
       @NotBlank String description,
       @NotBlank String location,
       LocalDateTime date,
        Integer capacity
) {
}
