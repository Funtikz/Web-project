package org.example.magazinexampleproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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
    private ClothingSize clothingSize;

    @Enumerated(EnumType.STRING)
    private ClothingCategory clothingCategory;
}
