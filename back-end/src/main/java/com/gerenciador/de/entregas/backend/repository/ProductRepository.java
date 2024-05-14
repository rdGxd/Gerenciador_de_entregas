package com.gerenciador.de.entregas.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerenciador.de.entregas.backend.models.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

  List<Product> findAllByUser_Id(String userId);

}
