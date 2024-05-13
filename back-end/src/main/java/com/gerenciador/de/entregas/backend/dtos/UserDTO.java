package com.gerenciador.de.entregas.backend.dtos;

import java.util.List;

import com.gerenciador.de.entregas.backend.models.product.Product;
import com.gerenciador.de.entregas.backend.models.user.UserRole;

public record UserDTO(String id, String name, String email, UserRole role, String createdAT, String updatedAT, List<Product> products) {
}
