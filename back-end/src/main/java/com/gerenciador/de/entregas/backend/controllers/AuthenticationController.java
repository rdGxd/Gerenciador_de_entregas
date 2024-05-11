package com.gerenciador.de.entregas.backend.controllers;

import com.gerenciador.de.entregas.backend.config.security.TokenService;
import com.gerenciador.de.entregas.backend.dtos.AuthenticationDTO;
import com.gerenciador.de.entregas.backend.dtos.LoginResponseDTO;
import com.gerenciador.de.entregas.backend.dtos.RegisterDTO;
import com.gerenciador.de.entregas.backend.models.user.User;
import com.gerenciador.de.entregas.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO data) {
        User user = userService.findByEmail(data.email());
        if (passwordEncoder.matches(data.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok().body(new LoginResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Validated RegisterDTO data) {
        if (userService.createUser(data)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
