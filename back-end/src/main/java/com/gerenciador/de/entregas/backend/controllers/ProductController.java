package com.gerenciador.de.entregas.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciador.de.entregas.backend.dtos.ProductDTO;
import com.gerenciador.de.entregas.backend.models.product.Product;
import com.gerenciador.de.entregas.backend.models.user.User;
import com.gerenciador.de.entregas.backend.service.ProductService;
import com.gerenciador.de.entregas.backend.service.UserService;


@RestController
@RequestMapping("/products")
public class ProductController {
  @Autowired
  private ProductService productService;

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<ProductDTO>> findAll(@RequestHeader(name = "Authorization") String token) {
    User user = userService.findByToken(token);
    List<Product> products = productService.findAll(user.getId());
    List<ProductDTO> newProducts = new ArrayList<>();
    for (Product product : products) {
      newProducts.add(new ProductDTO(product.getId(), product.getUser().getName(), product.getUser().getId() ,product.getName(), product.getCodigo(), product.getCreatedAT(), product.getUpdatedAT()));
  }

  return ResponseEntity.ok().body(newProducts);
}

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> findById(@PathVariable String id, @RequestHeader("Authorization") String token) {
    User user = userService.findByToken(token);
    Product product = productService.findById(id, user);
    ProductDTO productDTO = new ProductDTO(product.getId(), product.getUser().getName(), product.getUser().getId() ,product.getName(), product.getCodigo(), product.getCreatedAT(), product.getUpdatedAT());
    return ResponseEntity.ok().body(productDTO);
  }

  @PostMapping
  public ResponseEntity<Void> insert(@RequestBody ProductDTO entity, @RequestHeader("Authorization") String token) {
    User user = userService.findByToken(token);
    Product product = new Product(entity.name(), entity.codigo(), user);
    user.getProducts().add(product);
    productService.insert(product);
    return ResponseEntity.ok().build();
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id, @RequestHeader("Authorization") String token) {
    User user = userService.findByToken(token);
    productService.delete(id, user);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody ProductDTO entity, @RequestHeader("Authorization") String token) {
    User user = userService.findByToken(token);
    productService.update(id, user, entity);
    return ResponseEntity.ok().build();
  }
}
