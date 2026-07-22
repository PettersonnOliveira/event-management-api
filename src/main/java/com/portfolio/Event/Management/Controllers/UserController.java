package com.portfolio.Event.Management.Controllers;

import com.portfolio.Event.Management.DTOS.UserRequestDTO;
import com.portfolio.Event.Management.DTOS.UserResponseDTO;
import com.portfolio.Event.Management.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO dadosEntrada){
        UserResponseDTO userResponseDTO = userService.createUser(dadosEntrada);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll(){
        List<UserResponseDTO> listDtoResponse = userService.listAll();
        return ResponseEntity.ok(listDtoResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById( @PathVariable Long id){
       UserResponseDTO userResponseDTO = userService.findByid(id);
        return ResponseEntity.ok(userResponseDTO);
    }
}
