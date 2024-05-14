package com.gerenciador.de.entregas.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gerenciador.de.entregas.backend.dtos.ProductDTO;
import com.gerenciador.de.entregas.backend.models.product.Product;
import com.gerenciador.de.entregas.backend.models.user.User;
import com.gerenciador.de.entregas.backend.models.user.UserRole;
import com.gerenciador.de.entregas.backend.repository.ProductRepository;
import com.gerenciador.de.entregas.backend.service.exceptions.DatabaseException;
import com.gerenciador.de.entregas.backend.service.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  JdbcTemplate jdbcTemplate;

  public List<Product> findAll(String id) {
    try {
      return productRepository.findAllByUser_Id(id);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public Product findById(String id, User user) {
    try {
      if (productRepository.findById(id).isPresent() && verifyUserAndRole(id, user)) {
        return productRepository.findById(id).get();
      }
    } catch (Exception e) {
      throw new ResourceNotFoundException(id);
    }
    return null;
  }

  public Product insert(Product product) {
    try {
      return productRepository.save(product);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  public Product update(String id, User user, ProductDTO dto) {
    try {
      Product product = findById(id, user);
      if (updateData(product, dto)) {
        return productRepository.save(product);
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    return null;
  }

  public boolean updateData(Product product, ProductDTO dto) {
    boolean changeProduct = false;

    if (dto.name() != null && !dto.name().isBlank()) {
      product.setName(dto.name());
      changeProduct = true;
    }
    if (dto.codigo() != null && !dto.codigo().isBlank()) {
      product.setCodigo(dto.codigo());
      changeProduct = true;
    }

    return changeProduct;
  }

  public void delete(String id, User user) {
    try {
      Product product = findById(id, user);
      if (product != null) {
        jdbcTemplate.update("DELETE FROM TB_USER_PRODUCTS WHERE PRODUCTS_ID = ?", id);
        productRepository.delete(product);
      }
    } catch (Exception e) {
      throw new DatabaseException(e.getMessage());
    }
  }

  public boolean verifyUserAndRole(String id, User user) {
    if (productRepository.findById(id).isPresent()) {
      User productUser = productRepository.findById(id).get().getUser();
      return productUser.getId().equals(user.getId()) || user.getRole().equals(UserRole.ADMIN);
    }
    return false;
  }
}
