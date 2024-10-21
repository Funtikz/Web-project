package org.example.magazinexampleproject.controllers;

import lombok.AllArgsConstructor;
import org.example.magazinexampleproject.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/cart")
@AllArgsConstructor
public class CartController {
    private CartService cartService;


    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addItemToCart(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity){
        cartService.addItemToCart(productId, quantity);
        return new ResponseEntity<>("Product added to cart", HttpStatus.OK);
    }
}
