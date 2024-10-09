package org.example.magazinexampleproject;

import org.example.magazinexampleproject.models.products.ProductType;
import org.example.magazinexampleproject.models.products.*;
import org.example.magazinexampleproject.repositories.product.ProductRepository;
import org.example.magazinexampleproject.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import org.example.magazinexampleproject.dto.ProductDTO;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClothingProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductType(ProductType.CLOTHING);
        productDTO.setClothingSize(ClothingSize.L);
        productDTO.setClothingCategory(ClothingCategory.JERSEY);
        productDTO.setName("Winter Jacket");
        productDTO.setDescription("Warm winter jacket");
        productDTO.setOriginalPrice(100.0);
        productDTO.setDiscountPercentage(20);
        productDTO.setQuantity(50);
        productDTO.setImageUrl("http://example.com/images/jacket.jpg");

        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        Product product = productService.createProduct(productDTO);

        assertTrue(product instanceof ClothingProduct);
        assertEquals("Winter Jacket", product.getName());
        assertEquals(100.0, product.getOriginalPrice());
        assertEquals(80.0, product.getDiscountedPrice());
        assertEquals(20, product.getDiscountPercentage());
        assertEquals(50, product.getQuantity());
        assertEquals("http://example.com/images/jacket.jpg", product.getImageUrl());

        ClothingProduct clothingProduct = (ClothingProduct) product;
        assertEquals(ClothingSize.L, clothingProduct.getClothingSize());
        assertEquals(ClothingCategory.JERSEY, clothingProduct.getClothingCategory());
    }

    @Test
    void testCreateAccessoryProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductType(ProductType.ACCESSORY);
        productDTO.setAccessoryCategory(AccessoryCategory.SCARF);
        productDTO.setName("Warm Scarf");
        productDTO.setDescription("A cozy scarf for cold weather.");
        productDTO.setOriginalPrice(15.0);
        productDTO.setDiscountPercentage(33);
        productDTO.setQuantity(200);
        productDTO.setImageUrl("http://example.com/images/scarf.jpg");

        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        Product product = productService.createProduct(productDTO);

        assertTrue(product instanceof AccessoryProduct);
        assertEquals("Warm Scarf", product.getName());
        assertEquals(15.0, product.getOriginalPrice());
        assertEquals(10.05, product.getDiscountedPrice());
        assertEquals(33, product.getDiscountPercentage());
        assertEquals(200, product.getQuantity());
        assertEquals("http://example.com/images/scarf.jpg", product.getImageUrl());

        AccessoryProduct accessoryProduct = (AccessoryProduct) product;
        assertEquals(AccessoryCategory.SCARF, accessoryProduct.getAccessoryCategory());
    }


}
