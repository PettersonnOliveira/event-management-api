package com.portfolio.Event.Management.Services;

import com.portfolio.Event.Management.DTOS.RegistrationRequestDTO;
import com.portfolio.Event.Management.DTOS.RegistrationResponseDTO;
import com.portfolio.Event.Management.Entities.Event;
import com.portfolio.Event.Management.Entities.Registration;
import com.portfolio.Event.Management.Entities.User;
import com.portfolio.Event.Management.Exceptions.BusinessRuleException;
import com.portfolio.Event.Management.Exceptions.ResourceNotFoundException;
import com.portfolio.Event.Management.Repositories.EventRepository;
import com.portfolio.Event.Management.Repositories.RegistrationRepository;
import com.portfolio.Event.Management.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public RegistrationService(RegistrationRepository registrationRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public RegistrationResponseDTO createRegistration(RegistrationRequestDTO dadosEntrada){
        User user = userRepository.findById(dadosEntrada.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

        Event event = eventRepository.findById(dadosEntrada.eventId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        if (registrationRepository.existsByUserAndEvent(user,event)){
            throw new BusinessRuleException("Usuário ja inscrito neste evento");
        }

        if ( registrationRepository.countByEvent(event) >= event.getCapacity()){
            throw new BusinessRuleException("Capacidade de inscrições esgotadas!! ");
        }
        Registration registration = new Registration(user, event);
        registration.setRegistrationDate(LocalDateTime.now());
        Registration registrationSalved = registrationRepository.save(registration);
        return new RegistrationResponseDTO(registrationSalved);
    }

    public List<RegistrationResponseDTO> listAll(){
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(RegistrationResponseDTO::new)
                .toList();
    }

    public RegistrationResponseDTO findById(Long id){
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição não encontrada"));
        return new RegistrationResponseDTO(registration);
    }

    public void deletarRegistration(Long id){
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration não encontrada"));
        registrationRepository.delete(registration);
    }

    public List<RegistrationResponseDTO> listParticipantsByEvent(Long eventId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));
        List<Registration> registrations = registrationRepository.findByEvent(event);

        return registrations.stream()
                .map(RegistrationResponseDTO::new)
                .toList();

    }

}
