package org.example.magazinexampleproject.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.magazinexampleproject.models.AccessoryProduct;
import org.example.magazinexampleproject.models.ClothingProduct;
import org.example.magazinexampleproject.models.Product;
import org.example.magazinexampleproject.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchBy(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> accessoryCategory,
            @RequestParam Optional<String> clothingSize,
            @RequestParam Optional<String> clothingCategory,
            @RequestParam Optional<Double> minPrice,
            @RequestParam Optional<Double> maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> products = productService.searchProducts(name, minPrice, maxPrice, accessoryCategory, clothingSize, clothingCategory, page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @PostMapping("/accessory")
    public AccessoryProduct createAccessoryProduct(@Valid @RequestBody AccessoryProduct product) {
        return productService.createAccessoryProduct(product);
    }

    @PostMapping("/clothing")
    public ClothingProduct createClothingProduct(@Valid @RequestBody ClothingProduct product) {
        return productService.createClothingProduct(product);
    }

    @PutMapping("/accessory/{id}")
    public ResponseEntity<AccessoryProduct> updateAccessoryProduct(@PathVariable Long id,
                                                                   @Valid @RequestBody AccessoryProduct updatedProduct) {
        AccessoryProduct product = productService.updateAccessoryProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/clothing/{id}")
    public ResponseEntity<ClothingProduct> updateClothingProduct(@PathVariable Long id,
                                                                 @Valid @RequestBody ClothingProduct updatedProduct) {
        ClothingProduct product = productService.updateClothingProduct(id, updatedProduct);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product with id " + id + " was deleted successfully", HttpStatus.NO_CONTENT);
    }




}
