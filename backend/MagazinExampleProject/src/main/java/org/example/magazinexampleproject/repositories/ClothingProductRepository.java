package org.example.magazinexampleproject.repositories;

import org.example.magazinexampleproject.models.AccessoryProduct;
import org.example.magazinexampleproject.models.ClothingProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothingProductRepository extends JpaRepository<ClothingProduct, Long> {
    Page<ClothingProduct> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
