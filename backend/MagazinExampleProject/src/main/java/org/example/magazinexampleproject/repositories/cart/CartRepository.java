package org.example.magazinexampleproject.repositories.cart;

import org.example.magazinexampleproject.models.carts.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
