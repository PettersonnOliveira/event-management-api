package com.portfolio.Event.Management.Services;

import com.portfolio.Event.Management.DTOS.UserRequestDTO;
import com.portfolio.Event.Management.DTOS.UserResponseDTO;
import com.portfolio.Event.Management.Entities.User;
import com.portfolio.Event.Management.Exceptions.ResourceNotFoundException;
import com.portfolio.Event.Management.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO dadosEntrada) {
        User user = new User(dadosEntrada);
        User userSalved = userRepository.save(user);
        return new UserResponseDTO(userSalved);

    }

    public List<UserResponseDTO> listAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public UserResponseDTO findByid(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User não encontrado"));
        return new UserResponseDTO(user);
    }
}
