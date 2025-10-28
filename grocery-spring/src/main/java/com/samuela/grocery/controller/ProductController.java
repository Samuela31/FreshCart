package com.samuela.grocery.controller;

import java.util.Collections;
import java.util.List;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.samuela.grocery.dao.entity.ProductEntity;
import com.samuela.grocery.exception.ResourceNotFoundException;
import com.samuela.grocery.service.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    // Create a new product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@Valid @RequestBody ProductEntity product) {
        logger.info("Creating new product: {}", product.getProductName());
        ProductEntity createdProduct = productService.createProduct(product);
        logger.info("Product created successfully with ID: {}", createdProduct.getProductId());
        return ResponseEntity.ok(createdProduct);
    }

    // Get all products
    // Customer or not, everyone should be able to see it in website
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        logger.info("Fetching all products...");
        List<ProductEntity> products = productService.getAllProducts();

        if (products.isEmpty()) {
            logger.info("No products found.");
            return ResponseEntity.ok(Collections.emptyList());
        }

        logger.info("Found {} products.", products.size());
        return ResponseEntity.ok(products);
    }

    // Get product by ID
    // Anyone logged in or not should be able to search product
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable int id) {
        logger.info("Fetching product with ID: {}", id);
        return productService.getProductById(id)
                .map(product -> {
                    logger.info("Product found with ID: {}", id);
                    return ResponseEntity.ok(product);
                })
                .orElseThrow(() -> {
                    logger.warn("Product not found with ID: {}", id);
                    return new ResourceNotFoundException("Product not found with ID: " + id);
                });
    }
    
    // Search products by name (ignores case, substring accepted)
    // Anyone should be able to search
    @GetMapping("/search")
    public ResponseEntity<List<ProductEntity>> searchProducts(@RequestParam String name) {
        List<ProductEntity> products = productService.searchProductsByName(name);
        if (products.isEmpty()) {
        	logger.info("No products found with name containing: {}", name);
            return ResponseEntity.noContent().build();
        }
        logger.info("Found {} products.", products.size());
        return ResponseEntity.ok(products);
    }

    // Update an existing product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable int id,
                                                       @Valid @RequestBody ProductEntity updatedProduct) {
        logger.info("Updating product with ID: {}", id);
        ProductEntity updated = productService.updateProduct(id, updatedProduct);
        logger.info("Product updated successfully with ID: {}", updated.getProductId());
        return ResponseEntity.ok(updated);
    }

    // Delete a product by ID
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        logger.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        logger.info("Product deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
