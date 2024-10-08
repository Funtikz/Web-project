package org.example.magazinexampleproject.service;

import lombok.AllArgsConstructor;
import org.example.magazinexampleproject.exceptions.ProductNotFoundException;
import org.example.magazinexampleproject.models.AccessoryProduct;
import org.example.magazinexampleproject.models.ClothingProduct;
import org.example.magazinexampleproject.models.Product;
import org.example.magazinexampleproject.repositories.AccessoryProductRepository;
import org.example.magazinexampleproject.repositories.ClothingProductRepository;
import org.example.magazinexampleproject.repositories.ProductRepository;
import org.example.magazinexampleproject.repositories.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ProductService {
    AccessoryProductRepository accessoryProductRepository;
    ClothingProductRepository clothingProductRepository;
    ProductRepository productRepository;

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
    public Page<Product> searchProducts(Optional<String> name,
                                        Optional<Double> minPrice,
                                        Optional<Double> maxPrice,
                                        Optional<String> accessoryCategory,
                                        Optional<String> clothingSize,
                                        Optional<String> clothingCategory,
                                        int page,
                                        int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Product> spec = Specification.where(null);

        if (name.isPresent()) {
            spec = spec.and(ProductSpecification.hasName(name.get()));
        }

        if (minPrice.isPresent() && maxPrice.isPresent()) {
            spec = spec.and(ProductSpecification.hasPriceBetween(minPrice.get(), maxPrice.get()));
        } else {
            if (minPrice.isPresent()) {
                spec = spec.and(ProductSpecification.hasMinPrice(minPrice.get()));
            }
            if (maxPrice.isPresent()) {
                spec = spec.and(ProductSpecification.hasMaxPrice(maxPrice.get()));
            }
        }

        if (accessoryCategory.isPresent()) {
            spec = spec.and(ProductSpecification.hasAccessoryCategory(accessoryCategory.get()));
        }

        if (clothingCategory.isPresent()){
            spec = spec.and(ProductSpecification.hasClothingCategory(clothingCategory.get()));
        }

        if (clothingSize.isPresent()){
            spec = spec.and(ProductSpecification.hasClothingSize(clothingSize.get()));
        }


        return productRepository.findAll(spec, pageable);
    }

}
