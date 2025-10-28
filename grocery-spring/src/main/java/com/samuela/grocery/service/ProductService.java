package com.samuela.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuela.grocery.dao.ProductDao;
import com.samuela.grocery.dao.entity.ProductEntity;
import com.samuela.grocery.exception.ResourceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    // Create a new product
    public ProductEntity createProduct(ProductEntity product) {
        return productDao.save(product);
    }

    // Get all products
    public List<ProductEntity> getAllProducts() {
        return productDao.findAll();
    }

    // Get product by ID
    public Optional<ProductEntity> getProductById(int id) {
        return productDao.findById(id);
    }

    // Update product
    public ProductEntity updateProduct(int id, ProductEntity updatedProduct) {
        return productDao.findById(id).map(product -> {
            product.setProductName(updatedProduct.getProductName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setCategory(updatedProduct.getCategory());
            product.setImageUrl(updatedProduct.getImageUrl());
            return productDao.save(product);
        }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    // Delete product by ID
    public void deleteProduct(int id) {
        if (!productDao.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }
        productDao.deleteById(id);
    }
    
    // Search product by name (case-insensitive)
    public List<ProductEntity> searchProductsByName(String name) {
        return productDao.findByProductNameContainingIgnoreCase(name);
    }
}
