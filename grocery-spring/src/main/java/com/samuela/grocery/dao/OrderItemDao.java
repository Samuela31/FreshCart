package com.samuela.grocery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samuela.grocery.dao.entity.OrderItemEntity;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItemEntity, Integer> {

    // Find all items for a specific order
    List<OrderItemEntity> findByOrderOrderId(int orderId);

    // Find all items for a specific product
    List<OrderItemEntity> findByProductProductId(int productId);
}
