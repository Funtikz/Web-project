package org.example.magazinexampleproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotBlank(message = "Product description cannot be blank")
    private String description;

    @NotNull(message = "Product price cannot be null")
    @Positive(message = "Product price must be a positive number")
    private Double price;

    @NotNull(message = "Product quantity cannot be null")
    @Positive(message = "Product quantity must be a positive number")
    private Integer quantity;

    @NotBlank(message = "Image URL cannot be blank")
    private String imageUrl;

    @Positive(message = "Discount percentage must be a positive number")
    private Integer discountPercentage;
}
