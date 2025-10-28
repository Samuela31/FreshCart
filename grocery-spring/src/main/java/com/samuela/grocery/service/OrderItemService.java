package com.samuela.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuela.grocery.dao.OrderItemDao;
import com.samuela.grocery.dao.entity.OrderItemEntity;
import com.samuela.grocery.exception.ResourceNotFoundException;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    // Create a new order item
    public OrderItemEntity createOrderItem(OrderItemEntity orderItem) {
        return orderItemDao.save(orderItem);
    }

    // Get all order items
    public List<OrderItemEntity> getAllOrderItems() {
        return orderItemDao.findAll();
    }

    // Get order item by ID
    public Optional<OrderItemEntity> getOrderItemById(int id) {
        return orderItemDao.findById(id);
    }

    // Get all items for a specific order
    public List<OrderItemEntity> getItemsByOrderId(int orderId) {
        return orderItemDao.findByOrderOrderId(orderId);
    }

    // Get all items for a specific product
    public List<OrderItemEntity> getItemsByProductId(int productId) {
        return orderItemDao.findByProductProductId(productId);
    }

    // Update an order item
    public OrderItemEntity updateOrderItem(int id, OrderItemEntity updatedItem) {
        return orderItemDao.findById(id).map(item -> {
            item.setOrder(updatedItem.getOrder());
            item.setProduct(updatedItem.getProduct());
            item.setQuantity(updatedItem.getQuantity());
            item.setProductPrice(updatedItem.getProductPrice());
            // subtotal is automatically recalculated by @PreUpdate/@PrePersist
            return orderItemDao.save(item);
        }).orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id " + id));
    }

    // Delete an order item by ID
    public void deleteOrderItem(int id) {
        if (!orderItemDao.existsById(id)) {
            throw new ResourceNotFoundException("OrderItem not found with id " + id);
        }
        orderItemDao.deleteById(id);
    }
}

