package com.samuela.grocery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samuela.grocery.dao.entity.CategoryPromotionEntity;

public interface CategoryPromotionDao extends JpaRepository<CategoryPromotionEntity, Integer> {
    List<CategoryPromotionEntity> findByActiveTrue();
}
