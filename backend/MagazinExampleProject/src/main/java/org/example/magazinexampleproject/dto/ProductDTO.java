package org.example.magazinexampleproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.magazinexampleproject.models.products.AccessoryCategory;
import org.example.magazinexampleproject.models.products.ClothingCategory;
import org.example.magazinexampleproject.models.products.ClothingSize;
import org.example.magazinexampleproject.models.products.ProductType;

@Data
public class ProductDTO {

    @Schema(description = "If need update Product", hidden = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Schema(description = "ProductType has the following categories: CLOTHING\n" +
            "ACCESSORY\n" +
            "the presence of one of them is mandatory", example = "ACCESSORY")
    private ProductType productType;

    @NotBlank(message = "Name cannot be blank")
    @Schema(description = "Name cannot be blank", example = "Warm Scarf")
    private String name;

    @NotBlank(message = "Description description cannot be blank")
    @Schema(description ="Description cannot be blank", example = "A cozy scarf for cold weather.")
    private String description;

    @NotNull(message = "Original price cannot be null")
    @Positive(message = "Original price must be a positive number")
    @Schema(description = "Original price cannot be null", example = "100")
    private Double originalPrice;

    @Positive(message = "Discounted price must be a positive number")
    @Schema(description = "Discounted price can be null. It is calculated based on discountPercentage", hidden = true)
    private Double discountedPrice;

    @NotNull(message = "Discount must not be null")
    @Min(value = 0, message = "Discount must be at least 0%")
    @Max(value = 100, message = "Discount must be at most 100%")
    @Schema(description = "The discount is mandatory and must be in the range from 0 to 100%", example = "10")
    private Integer discountPercentage;

    @NotNull(message = "Product quantity cannot be null")
    @Positive(message = "Product quantity must be a positive number")
    @Schema(description = "Quantity cannot be null", example = "10")
    private Integer quantity;

    @NotBlank(message = "Image URL cannot be blank")
    @Schema(description = "Image URL cannot be blank", example = "http://example.com/images/warm-scarf.jpg")
    private String imageUrl;

    @Schema(description = "Required if you have productType = CLOTHING")
    private ClothingSize clothingSize;
    @Schema(description = "Required if you have productType = CLOTHING")
    private ClothingCategory clothingCategory;
    @Schema(description = "Required if you have productType = ACCESSORY")
    private AccessoryCategory accessoryCategory;

}

