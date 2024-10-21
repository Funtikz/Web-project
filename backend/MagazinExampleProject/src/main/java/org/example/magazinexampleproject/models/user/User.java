package org.example.magazinexampleproject.models.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.magazinexampleproject.models.carts.Cart;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cart cart; // Имя соответствует полю в UserDto

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles; // Роли пользователя
}
