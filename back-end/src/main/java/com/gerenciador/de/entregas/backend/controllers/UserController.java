package com.gerenciador.de.entregas.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciador.de.entregas.backend.dtos.RegisterDTO;
import com.gerenciador.de.entregas.backend.dtos.UserDTO;
import com.gerenciador.de.entregas.backend.models.user.User;
import com.gerenciador.de.entregas.backend.service.UserService;




@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> newUsers = new ArrayList<>();
        for (User newUser : users) {
            newUsers.add(new UserDTO(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getRole(), newUser.getCreatedAT(), newUser.getUpdatedAT(), newUser.getProducts()));
        }
        return ResponseEntity.ok().body(newUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
      User user = userService.findById(id);
      UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAT(), user.getUpdatedAT(), user.getProducts());
      return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody @Validated RegisterDTO entity) {
        userService.createUser(entity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestHeader("Authorization") String token, @RequestBody RegisterDTO userDTO) {
      userService.updated(id, token, userDTO);
      return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, @RequestHeader("Authorization") String token) {
      userService.delete(id, token);
      return ResponseEntity.ok().build();
    }
}
