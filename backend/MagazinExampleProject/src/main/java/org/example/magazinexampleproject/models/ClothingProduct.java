package org.example.magazinexampleproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clothing")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClothingProduct extends Product {
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Clothing size cannot be blank")
    private ClothingSize clothingSize;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Clothing category cannot be blank")
    private ClothingCategory clothingCategory;
}
