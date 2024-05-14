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

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  JdbcTemplate jdbcTemplate;

  public List<Product> findAll(String id) {
    return productRepository.findAllByUser_Id(id);
  }

  public Product findById(String id, User user) {
    try {
      if (productRepository.findById(id).isPresent() && verifyUserAndRole(id, user)) {
        return productRepository.findById(id).get();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void insert(Product product) {
    productRepository.save(product);
  }

  public void update(String id, User user, ProductDTO dto) {
    try {
      Product product = findById(id, user);
      updateData(product, dto);
      productRepository.save(product);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public void delete(String id, User user) {
    try {
      Product product = findById(id, user);
      if (product != null) {
        jdbcTemplate.update("DELETE FROM TB_USER_PRODUCTS WHERE PRODUCTS_ID = ?", id);
        productRepository.delete(product);
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public void updateData(Product product, ProductDTO dto) {
    if (dto.name() != null) {
      product.setName(dto.name());
    }
    if (dto.codigo() != null) {
      product.setCodigo(dto.codigo());
    }
  }

  public boolean verifyUserAndRole(String id, User user) {
    // Verifica se o link com o ID fornecido existe
    if (productRepository.findById(id).isPresent()) {
      // Obtém o usuário associado ao link
      User linkUser = productRepository.findById(id).get().getUser();
      // Verifica se o usuário é o proprietário do link ou se é um administrador
      return linkUser.getId().equals(user.getId()) || linkUser.getRole().equals(UserRole.ADMIN);
    }
    // Retorna false se o link não for encontrado
    return false;
  }
}
