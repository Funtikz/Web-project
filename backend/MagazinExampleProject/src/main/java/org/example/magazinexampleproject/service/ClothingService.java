package org.example.magazinexampleproject.service;

import lombok.RequiredArgsConstructor;
import org.example.magazinexampleproject.dto.ClothingDto;
import org.example.magazinexampleproject.exceptions.EmptyListException;
import org.example.magazinexampleproject.exceptions.ProductNotFoundException;
import org.example.magazinexampleproject.mappers.ClothingMapper;
import org.example.magazinexampleproject.models.ClothingProduct;
import org.example.magazinexampleproject.repositories.ClothingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothingService {
    private final ClothingRepository repository;
    private final ClothingMapper mapper;

    public ClothingDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<ClothingDto> getAll() {
        List<ClothingProduct> allProducts = repository.findAll();
        if (allProducts.isEmpty()) {
            throw new EmptyListException();
        }
        return mapper.listToDto(allProducts);
    }

    public ClothingDto save(ClothingDto clothingDto) {
        ClothingProduct entity = mapper.toEntity(clothingDto);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public ClothingDto changeQuantity(Long id, Integer quantity){
        ClothingProduct clothingProduct = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        int currentQuantity = clothingProduct.getQuantity() - quantity;
        if (currentQuantity < 0){
            throw new IllegalArgumentException("Quantity to increase must be greater than zero.");
        }
        clothingProduct.setQuantity(currentQuantity);
        repository.save(clothingProduct);
        return mapper.toDto(clothingProduct);
    }

    public ClothingDto increaseQuantity(Long id, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to increase must be greater than zero.");
        }
        return changeQuantity(id,quantity);
    }

    public ClothingDto reduceQuantity(Long id, Integer quantity) {
        if (quantity >= 0){
            throw new IllegalArgumentException("If you want to reduce the quantity, then the quantity should be negative");
        }
        return changeQuantity(id,quantity);
    }

}
