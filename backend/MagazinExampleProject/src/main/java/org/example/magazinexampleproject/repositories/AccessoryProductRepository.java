package org.example.magazinexampleproject.repositories;

import org.example.magazinexampleproject.models.AccessoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryProductRepository extends JpaRepository<AccessoryProduct, Long> {

}
