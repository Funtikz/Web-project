package org.example.magazinexampleproject.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.magazinexampleproject.dto.product.ProductDTO;
import org.example.magazinexampleproject.models.products.Product;
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
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.updateProduct(productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product with id " + id + " was deleted successfully", HttpStatus.NO_CONTENT);
    }

}
