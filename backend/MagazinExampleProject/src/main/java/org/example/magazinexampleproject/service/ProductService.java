package org.example.magazinexampleproject.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.magazinexampleproject.dto.product.ProductDTO;
import org.example.magazinexampleproject.exceptions.ProductNotFoundException;
import org.example.magazinexampleproject.models.products.AccessoryProduct;
import org.example.magazinexampleproject.models.products.ClothingProduct;
import org.example.magazinexampleproject.models.products.Product;
import org.example.magazinexampleproject.repositories.product.ProductRepository;
import org.example.magazinexampleproject.repositories.product.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    ProductRepository productRepository;

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product;

        switch (productDTO.getProductType()) {
            case CLOTHING:
                ClothingProduct clothingProduct = new ClothingProduct();
                clothingProduct.setClothingSize(productDTO.getClothingSize());
                clothingProduct.setClothingCategory(productDTO.getClothingCategory());
                product = clothingProduct;
                break;

            case ACCESSORY:
                AccessoryProduct accessoryProduct = new AccessoryProduct();
                accessoryProduct.setAccessoryCategory(productDTO.getAccessoryCategory());
                product = accessoryProduct;
                break;

            default:
                throw new IllegalArgumentException("Invalid product type: " + productDTO.getProductType());
        }
        setCommonFields(product, productDTO);
        return productRepository.save(product);
    }

    private void setCommonFields(Product product, ProductDTO productDTO){
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setOriginalPrice(productDTO.getOriginalPrice());
        product.setDiscountedPrice(calculateDiscountPrice(productDTO));
        product.setDiscountPercentage(productDTO.getDiscountPercentage());
        product.setQuantity(productDTO.getQuantity());
        product.setImageUrl(productDTO.getImageUrl());
        product.setProductType(productDTO.getProductType());
    }

    public double calculateDiscountPrice(ProductDTO product){
        return product.getOriginalPrice()
                - (product.getOriginalPrice() * product.getDiscountPercentage() / 100);

    }

    @Transactional
    public Product updateProduct(ProductDTO productDTO) {
        Product existingProduct = getProductById(productDTO.getId());

        switch (productDTO.getProductType()) {
            case CLOTHING:
                if (existingProduct instanceof ClothingProduct) {
                    ClothingProduct clothingProduct = (ClothingProduct) existingProduct;
                    clothingProduct.setClothingCategory(productDTO.getClothingCategory());
                    clothingProduct.setClothingSize(productDTO.getClothingSize());
                } else {
                    throw new IllegalArgumentException("Product type mismatch. Expected ClothingProduct.");
                }
                break;

            case ACCESSORY:
                if (existingProduct instanceof AccessoryProduct) {
                    AccessoryProduct accessoryProduct = (AccessoryProduct) existingProduct;
                    accessoryProduct.setAccessoryCategory(productDTO.getAccessoryCategory());
                } else {
                    throw new IllegalArgumentException("Product type mismatch. Expected AccessoryProduct.");
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid product type: " + productDTO.getProductType());
        }
        setCommonFields(existingProduct, productDTO);
        return productRepository.save(existingProduct);
    }
    @Transactional
    public void deleteProduct(Long id) {
        Product productById = getProductById(id);
        productRepository.delete(productById);
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
