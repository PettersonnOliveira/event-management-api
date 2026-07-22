package com.portfolio.Event.Management.Controllers;

import com.portfolio.Event.Management.DTOS.RegistrationRequestDTO;
import com.portfolio.Event.Management.DTOS.RegistrationResponseDTO;
import com.portfolio.Event.Management.Services.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationsController {
    private final RegistrationService registrationService;

    public RegistrationsController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> createRegistration( @Valid @RequestBody RegistrationRequestDTO dadosEntrada){

        RegistrationResponseDTO registrationResponseDTO = registrationService.createRegistration(dadosEntrada);

        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponseDTO);
    }

    @GetMapping("/events/{eventId}/participants")
    public ResponseEntity<List<RegistrationResponseDTO>> listParticipants(@PathVariable Long eventId){

        List<RegistrationResponseDTO> responseDTOList = registrationService.listParticipantsByEvent(eventId);

        return ResponseEntity.ok(responseDTOList);
    }


@DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar( @PathVariable Long id){
        registrationService.deletarRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
