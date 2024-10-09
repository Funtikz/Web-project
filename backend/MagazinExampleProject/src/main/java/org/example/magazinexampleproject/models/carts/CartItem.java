package org.example.magazinexampleproject.models.carts;

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

    @ManyToOne
    private Cart cart;

    @OneToOne
    private Product product;

    private int quantity;

    private double price;
}
