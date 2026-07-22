package com.portfolio.Event.Management.DTOS;

import jakarta.validation.constraints.NotNull;

public record RegistrationRequestDTO(
      @NotNull Long userId,
      @NotNull  Long eventId
) {
}
