package org.example.magazinexampleproject.models.carts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.magazinexampleproject.models.products.Product;

@Entity
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference // Это предотвратит рекурсию
    private Cart cart;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    private int quantity;

    private double price;
}
