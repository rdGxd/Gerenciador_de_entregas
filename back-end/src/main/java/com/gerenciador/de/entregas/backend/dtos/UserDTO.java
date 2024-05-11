package com.gerenciador.de.entregas.backend.dtos;

import com.gerenciador.de.entregas.backend.models.user.UserRole;

public record UserDTO(String id, String name, String email, UserRole role) {
}
