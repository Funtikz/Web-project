package org.example.magazinexampleproject.repositories.product;

import org.example.magazinexampleproject.models.products.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<Product> hasAccessoryCategory(String accessoryCategory) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("accessoryCategory"), accessoryCategory);
    }

    public static Specification<Product> hasClothingCategory(String clothingCategory){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("clothingCategory"), clothingCategory));
    }

    public static Specification<Product> hasClothingSize(String size) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("clothingSize"), size);
    }

    public static Specification<Product> hasMinPrice(Double minPrice){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
    }

    public static Specification<Product> hasMaxPrice(Double maxPrice){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
    }
}
