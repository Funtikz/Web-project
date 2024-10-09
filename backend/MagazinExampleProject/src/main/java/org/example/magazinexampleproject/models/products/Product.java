package org.example.magazinexampleproject.models.products;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Data
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private String name;

    private String description;

    private Double originalPrice;

    private Double discountedPrice;

    private Integer discountPercentage;

    private Integer quantity;

    private String imageUrl;
}
