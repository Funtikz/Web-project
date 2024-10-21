package org.example.magazinexampleproject.dto.user;

import lombok.Data;
import org.example.magazinexampleproject.models.carts.Cart;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String password;
    private String email;
    private Set<String> roles; // Имя соответствует полю в User
    private Cart cart; // Имя соответствует полю в User
}
