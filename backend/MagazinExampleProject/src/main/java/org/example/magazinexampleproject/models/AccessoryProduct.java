package org.example.magazinexampleproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accessory")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccessoryProduct extends Product {
    @Enumerated(EnumType.STRING)
    private AccessoryCategory accessoryCategory;
}
