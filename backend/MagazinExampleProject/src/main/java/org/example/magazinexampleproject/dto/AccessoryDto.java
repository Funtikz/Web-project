package org.example.magazinexampleproject.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.magazinexampleproject.models.AccessoryCategory;

@Data
public class AccessoryDto {
    @NotBlank
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    private String description;

    @Positive(message = "Price must be positive")
    private Double price;

    @Min(value = 0, message = "Quantity must be at least 0")
    private Integer quantity;

    @NotBlank(message = "Image URL must not be blank")
    private String imageUrl;

    @Max(value = 100, message = "Maximum discount is 100%")
    @Min(value = 1, message = "Minimum discount is 1%")
    private Integer discountPercentage;

    @NotNull(message = "Accessory category must not be null")
    private AccessoryCategory accessoryCategory;

}
