package org.example.magazinexampleproject.repositories.cart;

import org.example.magazinexampleproject.models.carts.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
