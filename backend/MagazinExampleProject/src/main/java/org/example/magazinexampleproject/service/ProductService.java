package org.example.magazinexampleproject.service;

import lombok.AllArgsConstructor;
import org.example.magazinexampleproject.exceptions.ProductNotFoundException;
import org.example.magazinexampleproject.models.AccessoryProduct;
import org.example.magazinexampleproject.models.ClothingProduct;
import org.example.magazinexampleproject.models.Product;
import org.example.magazinexampleproject.repositories.AccessoryProductRepository;
import org.example.magazinexampleproject.repositories.ClothingProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ProductService {
    AccessoryProductRepository accessoryProductRepository;
    ClothingProductRepository clothingProductRepository;

    public AccessoryProduct createAccessoryProduct(AccessoryProduct product) {
        return accessoryProductRepository.save(product);
    }

    public ClothingProduct createClothingProduct(ClothingProduct product) {
        return clothingProductRepository.save(product);
    }
    public List<Product> getAllProducts() {
        return Stream.concat(
                accessoryProductRepository.findAll().stream(),
                clothingProductRepository.findAll().stream()
        ).toList();
    }


    public Product getProductById(Long id) {
        return accessoryProductRepository.findById(id)
                .map(product -> (Product) product)
                .or(() -> clothingProductRepository.findById(id).map(product -> (Product) product))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public AccessoryProduct updateAccessoryProduct(Long id, AccessoryProduct updatedProduct) {
        return accessoryProductRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setQuantity(updatedProduct.getQuantity());
                    existingProduct.setImageUrl(updatedProduct.getImageUrl());
                    existingProduct.setDiscountPercentage(updatedProduct.getDiscountPercentage());
                    existingProduct.setAccessoryCategory(updatedProduct.getAccessoryCategory());
                    return accessoryProductRepository.save(existingProduct);
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


    public ClothingProduct updateClothingProduct(Long id, ClothingProduct updatedProduct) {
        return clothingProductRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setQuantity(updatedProduct.getQuantity());
                    existingProduct.setImageUrl(updatedProduct.getImageUrl());
                    existingProduct.setDiscountPercentage(updatedProduct.getDiscountPercentage());
                    existingProduct.setClothingCategory(updatedProduct.getClothingCategory());
                    existingProduct.setClothingSize(updatedProduct.getClothingSize());
                    return clothingProductRepository.save(existingProduct);
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


    public void deleteProduct(Long id) {
        if (accessoryProductRepository.existsById(id)) {
            accessoryProductRepository.deleteById(id);
        } else if (clothingProductRepository.existsById(id)) {
            clothingProductRepository.deleteById(id);
        } else {
            throw new ProductNotFoundException(id);
        }
    }
    public Page<Product> searchProductsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<AccessoryProduct> accessoryPage = accessoryProductRepository.findByNameContainingIgnoreCase(name, pageable);
        Page<ClothingProduct> clothingPage = clothingProductRepository.findByNameContainingIgnoreCase(name, pageable);

        List<Product> combinedProducts = new ArrayList<>();
        combinedProducts.addAll(accessoryPage.getContent());
        combinedProducts.addAll(clothingPage.getContent());

        long totalElements = accessoryPage.getTotalElements() + clothingPage.getTotalElements();
        return new PageImpl<>(combinedProducts, pageable, totalElements);
    }

}
