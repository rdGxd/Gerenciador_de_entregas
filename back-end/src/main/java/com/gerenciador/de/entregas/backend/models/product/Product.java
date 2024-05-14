package com.gerenciador.de.entregas.backend.models.product;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gerenciador.de.entregas.backend.models.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_product")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String codigo;

    @NonNull
    @CreationTimestamp
    private String createdAT;
    @NonNull
    @UpdateTimestamp
    private String updatedAT;

    @JsonIgnore
    @ManyToOne
    @NonNull
    private User user;

    public Product(String name, String codigo, User user) {
        this.name = name;
        this.codigo = codigo;
        this.user = user;
    }


}
