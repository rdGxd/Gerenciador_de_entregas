package com.gerenciador.de.entregas.backend.dtos;

import com.gerenciador.de.entregas.backend.models.user.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role) {
}
