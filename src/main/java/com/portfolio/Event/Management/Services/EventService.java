package com.portfolio.Event.Management.Services;

import com.portfolio.Event.Management.DTOS.EventRequestDTO;
import com.portfolio.Event.Management.DTOS.EventResponseDTO;
import com.portfolio.Event.Management.DTOS.EventUpdateDTO;
import com.portfolio.Event.Management.Entities.Event;
import com.portfolio.Event.Management.Exceptions.ResourceNotFoundException;
import com.portfolio.Event.Management.Repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventResponseDTO createEvent(EventRequestDTO dadosEntrada){
        Event event = new Event(dadosEntrada);
        Event eventSalved = eventRepository.save(event);
        return new EventResponseDTO(eventSalved);
    }

    public List<EventResponseDTO> listALL(){
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(EventResponseDTO::new)
                .toList();
    }

    public EventResponseDTO findById(Long id){
        Event event = eventRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Event não encontrado"));
        return new EventResponseDTO(event);
    }

    public EventResponseDTO update(Long id, EventUpdateDTO dadosUpdate){
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));
        event.setTitle(dadosUpdate.title());
        event.setDescription(dadosUpdate.description());
        event.setLocation(dadosUpdate.location());
        event.setDate(dadosUpdate.date());
        event.setCapacity(dadosUpdate.capacity());

        Event eventUpdate = eventRepository.save(event);
        return  new EventResponseDTO(eventUpdate);

    }

    public void delete(long id){
        Event event = eventRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Event não encontrado"));
        eventRepository.delete(event);
    }
}
