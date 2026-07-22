package com.portfolio.Event.Management.Controllers;

import com.portfolio.Event.Management.DTOS.EventRequestDTO;
import com.portfolio.Event.Management.DTOS.EventResponseDTO;
import com.portfolio.Event.Management.DTOS.EventUpdateDTO;
import com.portfolio.Event.Management.Entities.Event;
import com.portfolio.Event.Management.Services.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody @Valid EventRequestDTO dadosEntrada){
        EventResponseDTO eventResponseDTO = eventService.createEvent(dadosEntrada);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> listAll(){
        List<EventResponseDTO> listResponseDTO = eventService.listALL();
        return ResponseEntity.ok(listResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> findById( @PathVariable  Long id){
        EventResponseDTO eventResponseDTO = eventService.findById(id);
        return ResponseEntity.ok(eventResponseDTO);
    }

@PutMapping("/{id}")
public ResponseEntity<EventResponseDTO> update(@PathVariable  Long id, @RequestBody EventUpdateDTO dadosUpdate){
        EventResponseDTO eventResponseDTO = eventService.update(id,dadosUpdate);
        return ResponseEntity.ok(eventResponseDTO);
}



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
