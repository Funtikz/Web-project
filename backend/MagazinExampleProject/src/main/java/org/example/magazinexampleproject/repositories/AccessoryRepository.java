package org.example.magazinexampleproject.repositories;

import org.example.magazinexampleproject.models.AccessoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends JpaRepository<AccessoryProduct, Long> {

}
