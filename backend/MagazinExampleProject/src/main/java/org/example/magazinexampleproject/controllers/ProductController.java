package org.example.magazinexampleproject.controllers;

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
    public ResponseEntity<Page<Product>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> products = productService.searchProductsByName(name, page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/accessory")
    public AccessoryProduct createAccessoryProduct(@RequestBody AccessoryProduct product) {
        return productService.createAccessoryProduct(product);
    }

    @PostMapping("/clothing")
    public ClothingProduct createClothingProduct(@RequestBody ClothingProduct product) {
        return productService.createClothingProduct(product);
    }

    @PutMapping("/accessory/{id}")
    public ResponseEntity<AccessoryProduct> updateAccessoryProduct(@PathVariable Long id,
                                                                   @RequestBody AccessoryProduct updatedProduct) {
        AccessoryProduct product = productService.updateAccessoryProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/clothing/{id}")
    public ResponseEntity<ClothingProduct> updateClothingProduct(@PathVariable Long id,
                                                                 @RequestBody ClothingProduct updatedProduct) {
        ClothingProduct product = productService.updateClothingProduct(id, updatedProduct);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }




}
