package com.gerenciador.de.entregas.backend.models.product;

import com.gerenciador.de.entregas.backend.models.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_product")
@Getter
@Setter
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
    private Date createdAT;
    @NonNull
    @UpdateTimestamp
    private Date updatedAT;

    @NonNull
    @ManyToOne
    private User user;
}
