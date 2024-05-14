package com.gerenciador.de.entregas.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gerenciador.de.entregas.backend.config.security.TokenService;
import com.gerenciador.de.entregas.backend.dtos.RegisterDTO;
import com.gerenciador.de.entregas.backend.models.user.User;
import com.gerenciador.de.entregas.backend.models.user.UserRole;
import com.gerenciador.de.entregas.backend.repository.UserRepository;
import com.gerenciador.de.entregas.backend.service.exceptions.ResourceNotFoundException;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  public List<User> findAll() {
    try {
      return userRepository.findAll();
    } catch (RuntimeException e) {
      throw new RuntimeException(e);
    }
  }

  public User findById(String id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
  }

  public User insert(User user) {
    return userRepository.save(user);
  }

  public User updated(String id, String token, RegisterDTO userDTO) {
    try {
      User userToken = findByToken(token);
      User userId = userRepository.getReferenceById(id);
      if (userToken.getId().equals(userId.getId()) && updatedData(userId, userDTO)) {
        return userRepository.save(userId);
      }
    } catch (RuntimeException e) {
      throw new RuntimeException(e.getMessage());
    }
    return null;
  }

  public boolean updatedData(User user, RegisterDTO userDTO) {
    boolean updatedUser = false;

    if (userDTO.name() != null && !userDTO.name().isBlank()) {
      user.setName(userDTO.name());
      updatedUser = true;
    }
    if (userDTO.password() != null && !userDTO.password().isBlank()) {
      String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());
      user.setPassword(encryptedPassword);
      updatedUser = true;
    }
    if (userDTO.email() != null && !userDTO.email().isBlank()) {
      user.setEmail(userDTO.email());
      updatedUser = true;
    }

    return updatedUser;
  }

  public void delete(String id, String token) {
    User user = userRepository.getReferenceById(id);
    User userToken = findByToken(token);
    if (user.getId().equals(userToken.getId()) | user.getRole() == UserRole.ADMIN) {
      userRepository.deleteById(id);
    } else {
      throw new RuntimeException("DEU RUIM 2");
    }
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow();
  }

  public User createUser(RegisterDTO data) {
    Optional<User> user = userRepository.findByEmail(data.email());

    if (user.isPresent()) {
      throw new RuntimeException("Conta já existe!");
    }

    if (data.email().length() < 3 || data.email().length() > 16 || data.password().length() < 6
        || data.password().length() > 16) {
      throw new RuntimeException("DEU RUIM 3");
    }

    try {
      String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
      User newUser = new User(data.name(), data.email(), encryptedPassword, data.role());
      return insert(newUser);
    } catch (RuntimeException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public User findByToken(String token) {
    token = token.replace("Bearer ", "");
    String name = tokenService.validateToken(token);
    return userRepository.findByEmail(name).get();
  }
}
