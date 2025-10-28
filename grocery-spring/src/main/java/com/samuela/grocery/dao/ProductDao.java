package com.samuela.grocery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samuela.grocery.dao.entity.ProductEntity;


@Repository
public interface ProductDao extends JpaRepository<ProductEntity, Integer> {
	
	// Search product by name (case insensitive, substring name allowed)
    List<ProductEntity> findByProductNameContainingIgnoreCase(String productName);

}

