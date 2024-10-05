package org.example.magazinexampleproject.repositories;

import org.example.magazinexampleproject.models.ClothingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClothingRepository extends JpaRepository<ClothingProduct, Long> {
    Optional<ClothingProduct> findById(Long id);
}
