package org.example.magazinexampleproject.dto.carts;

import lombok.Data;
import org.example.magazinexampleproject.models.carts.Cart;
import org.example.magazinexampleproject.models.products.Product;

@Data

public class CartItemDto {
    private Long id;
    private Cart cart;
    private Product product;
    private int quantity;
    private double price;
}
