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

import com.samuela.grocery.dao.entity.OrderItemEntity;
import com.samuela.grocery.exception.ResourceNotFoundException;
import com.samuela.grocery.service.OrderItemService;


@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemController.class);

    @Autowired
    private OrderItemService orderItemService;

    // Create new order item
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @PostMapping
    public ResponseEntity<OrderItemEntity> createOrderItem(@Valid @RequestBody OrderItemEntity orderItem) {
        logger.info("Creating a new order item...");
        OrderItemEntity savedItem = orderItemService.createOrderItem(orderItem);
        logger.info("Order item created successfully with ID: {}", savedItem.getOrderItemId());
        return ResponseEntity.ok(savedItem);
    }

    // Get all order items
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllOrderItems() {
        logger.info("Fetching all order items...");
        List<OrderItemEntity> items = orderItemService.getAllOrderItems();

        if (items.isEmpty()) {
            logger.info("No order items found.");
            return ResponseEntity.ok(Collections.emptyList());
        }

        logger.info("Found {} order items.", items.size());
        return ResponseEntity.ok(items);
    }


    // Get order item by ID
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemEntity> getOrderItemById(@PathVariable int id) {
        logger.info("Fetching order item with ID: {}", id);
        return orderItemService.getOrderItemById(id)
                .map(item -> {
                    logger.info("Order item found with ID: {}", id);
                    return ResponseEntity.ok(item);
                })
                .orElseThrow(() -> {
                    logger.warn("Order item not found with ID: {}", id);
                    return new ResourceNotFoundException("OrderItem not found with ID: " + id);
                });
    }

    // Get all order items for a specific order
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemEntity>> getItemsByOrderId(@PathVariable int orderId) {
        logger.info("Fetching items for order ID: {}", orderId);
        List<OrderItemEntity> items = orderItemService.getItemsByOrderId(orderId);

        if (items.isEmpty()) {
            logger.info("No items found for order ID: {}", orderId);
            return ResponseEntity.ok(Collections.emptyList());
        }

        logger.info("Found {} items for order ID: {}", items.size(), orderId);
        return ResponseEntity.ok(items);
    }

    // Get all order items for a specific product
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderItemEntity>> getItemsByProductId(@PathVariable int productId) {
        logger.info("Fetching items for product ID: {}", productId);
        List<OrderItemEntity> items = orderItemService.getItemsByProductId(productId);

        if (items.isEmpty()) {
            logger.info("No items found for product ID: {}", productId);
            return ResponseEntity.ok(Collections.emptyList());
        }

        logger.info("Found {} items for product ID: {}", items.size(), productId);
        return ResponseEntity.ok(items);
    }

    // Update an order item
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemEntity> updateOrderItem(@PathVariable int id,
                                                           @Valid @RequestBody OrderItemEntity updatedItem) {
        logger.info("Updating order item with ID: {}", id);
        OrderItemEntity item = orderItemService.updateOrderItem(id, updatedItem);
        logger.info("Order item with ID {} updated successfully.", id);
        return ResponseEntity.ok(item);
    }

    // Delete an order item
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable int id) {
        logger.info("Deleting order item with ID: {}", id);
        orderItemService.deleteOrderItem(id);
        logger.info("Order item deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
