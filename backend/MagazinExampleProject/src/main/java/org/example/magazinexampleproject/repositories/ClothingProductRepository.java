package org.example.magazinexampleproject.repositories;

import org.example.magazinexampleproject.models.ClothingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingProductRepository extends JpaRepository<ClothingProduct, Long> {
}
