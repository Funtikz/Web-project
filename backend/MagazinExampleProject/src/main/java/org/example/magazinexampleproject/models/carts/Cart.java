package org.example.magazinexampleproject.models.carts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.magazinexampleproject.models.user.User;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Это управляет сериализацией
    private List<CartItem> items = new ArrayList<>();

    private double totalPrice;

    @OneToOne(mappedBy = "cart", fetch = FetchType.EAGER) // Используйте mappedBy для указания обратной связи
    @JsonBackReference // Это предотвратит рекурсию
    private User user;
}

