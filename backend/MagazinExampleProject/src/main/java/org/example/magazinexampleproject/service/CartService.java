package org.example.magazinexampleproject.service;

import lombok.AllArgsConstructor;
import org.example.magazinexampleproject.dto.user.UserDto;
import org.example.magazinexampleproject.models.carts.Cart;
import org.example.magazinexampleproject.models.carts.CartItem;
import org.example.magazinexampleproject.models.products.Product;
import org.example.magazinexampleproject.repositories.cart.CartItemRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {
    private CartItemRepository cartItemRepository;
    private ProductService productService;
    private UserService userService;

    public void addItemToCart(Long productId, int quantity) {
        // Получаем текущего пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByEmail(authentication.getName());

        // Проверяем корректность количества
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        // Получаем продукт по его ID
        Product product = productService.getProductById(productId);

        // Проверяем, достаточно ли товара в наличии
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }

        // Получаем корзину пользователя
        Cart cart = user.getCart();

        // Проверяем, есть ли уже этот товар в корзине
        CartItem existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            // Если товар уже в корзине, обновляем количество и цену
            if (existingCartItem.getQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setPrice(existingCartItem.getQuantity() * product.getDiscountedPrice());
            cart.setTotalPrice(cart.getTotalPrice()+ product.getDiscountedPrice() *quantity);
            cart.setTotalPrice(cart.getTotalPrice() + existingCartItem.getPrice());
            cartItemRepository.save(existingCartItem);
        } else {
            // Если товара нет в корзине, добавляем его
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(quantity * product.getDiscountedPrice());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
            cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
            cartItemRepository.save(cartItem);
        }
    }


}
