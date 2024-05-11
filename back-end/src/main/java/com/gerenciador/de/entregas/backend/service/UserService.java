package com.gerenciador.de.entregas.backend.service;

import com.gerenciador.de.entregas.backend.dtos.RegisterDTO;
import com.gerenciador.de.entregas.backend.dtos.UserDTO;
import com.gerenciador.de.entregas.backend.models.user.User;
import com.gerenciador.de.entregas.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> newUsers = new ArrayList<>();
        for (User newUser : users) {
            newUsers.add(new UserDTO(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getRole()));
        }
        return newUsers;
    }

    public void insert(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public boolean createUser(RegisterDTO data) {
        Optional<User> user = userRepository.findByEmail(data.email());

        if (user.isPresent() || data.email().length() < 3 || data.email().length() > 16 || data.password().length() < 6 || data.password().length() > 16) {
            return false;
        }

        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User newUser = new User(data.name(), data.email(), encryptedPassword, data.role());
            insert(newUser);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
