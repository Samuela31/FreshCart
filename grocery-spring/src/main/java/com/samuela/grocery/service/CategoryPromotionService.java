package com.samuela.grocery.service;

import com.samuela.grocery.dao.CategoryPromotionDao;
import com.samuela.grocery.dao.entity.CategoryPromotionEntity;
import com.samuela.grocery.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryPromotionService {

    @Autowired
    private CategoryPromotionDao categoryPromotionDao;

    // Create a new category promotion
    public CategoryPromotionEntity createPromotion(CategoryPromotionEntity promotion) {
        return categoryPromotionDao.save(promotion);
    }

    // Get all active promotions
    public List<CategoryPromotionEntity> getActivePromotions() {
        return categoryPromotionDao.findByActiveTrue();
    }


    // Stop a promotion
    public CategoryPromotionEntity stopPromotion(int id) {
        CategoryPromotionEntity promo = categoryPromotionDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion not found with id: " + id));
        promo.setActive(false);
        return categoryPromotionDao.save(promo);
    }

    // Get promotion by ID
    public CategoryPromotionEntity getPromotionById(int id) {
        return categoryPromotionDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion not found with id: " + id));
    }

    // Get all promotions (active + inactive)
    public List<CategoryPromotionEntity> getAllPromotions() {
        return categoryPromotionDao.findAll();
    }
    
    // Delete promotion by ID
    public void deletePromotion(int id) {
        if (!categoryPromotionDao.existsById(id)) {
            throw new ResourceNotFoundException("Promotion not found with id " + id);
        }
        categoryPromotionDao.deleteById(id);
    }
}
