package com.samuela.grocery.controller;

import com.samuela.grocery.dao.entity.CategoryPromotionEntity;
import com.samuela.grocery.service.CategoryPromotionService;

import jakarta.validation.Valid;

import com.samuela.grocery.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/category-promotions")
public class CategoryPromotionController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryPromotionController.class);

    private final CategoryPromotionService promotionService;

    @Autowired
    public CategoryPromotionController(CategoryPromotionService promotionService) {
        this.promotionService = promotionService;
    }

    // Create a new category promotion
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryPromotionEntity> createPromotion(
            @Valid @RequestBody CategoryPromotionEntity promotion) {

        logger.info("Creating new category promotion for category: {}", promotion.getCategory());
        CategoryPromotionEntity savedPromotion = promotionService.createPromotion(promotion);
        logger.info("Category promotion created successfully with ID: {}", savedPromotion.getId());
        return ResponseEntity.ok(savedPromotion); // 200 OK
    }

    // Get all active category promotions 
    // (don't restrict access here, everyone should be able to see discounts)
    @GetMapping("/active")
    public ResponseEntity<List<CategoryPromotionEntity>> getActivePromotions() {
        logger.info("Fetching all active category promotions...");
        List<CategoryPromotionEntity> activePromotions = promotionService.getActivePromotions();

        if (activePromotions.isEmpty()) {
            logger.info("No active category promotions found.");
            return ResponseEntity.ok(Collections.emptyList());
        }

        logger.info("Found {} active promotions.", activePromotions.size());
        return ResponseEntity.ok(activePromotions);
    }

    // Stop a category promotion
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/stop")
    public ResponseEntity<CategoryPromotionEntity> stopPromotion(@PathVariable int id) {
        logger.info("Stopping category promotion with ID: {}", id);
        CategoryPromotionEntity stoppedPromotion = promotionService.stopPromotion(id);
        logger.info("Category promotion with ID {} stopped successfully.", id);
        return ResponseEntity.ok(stoppedPromotion);
    }

    // Get promotion by ID
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryPromotionEntity> getPromotionById(@PathVariable int id) {
        logger.info("Fetching category promotion with ID: {}", id);
        CategoryPromotionEntity promotion = promotionService.getPromotionById(id);

        if (promotion == null) {
            logger.warn("Category promotion not found with ID: {}", id);
            throw new ResourceNotFoundException("Category promotion not found with ID: " + id);
        }

        logger.info("Category promotion found with ID: {}", id);
        return ResponseEntity.ok(promotion);
    }

    // Get all promotions (active + inactive)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CategoryPromotionEntity>> getAllPromotions() {
        logger.info("Fetching all category promotions (active + inactive)...");
        List<CategoryPromotionEntity> promotions = promotionService.getAllPromotions();

        if (promotions.isEmpty()) {
            logger.info("No category promotions found in database.");
            return ResponseEntity.ok(Collections.emptyList());
        }

        logger.info("Found {} promotions.", promotions.size());
        return ResponseEntity.ok(promotions);
    }
    
    // Delete promotion
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable int id) {
        logger.info("Deleting promotion with ID: {}", id);
        promotionService.deletePromotion(id);
        logger.info("Promotion deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
