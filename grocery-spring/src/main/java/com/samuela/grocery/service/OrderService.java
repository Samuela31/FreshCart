package com.samuela.grocery.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samuela.grocery.dao.CustomerDao;
import com.samuela.grocery.dao.OrderDao;
import com.samuela.grocery.dao.PaymentDao;
import com.samuela.grocery.dao.ProductDao;
import com.samuela.grocery.dao.entity.CustomerEntity;
import com.samuela.grocery.dao.entity.OrderEntity;
import com.samuela.grocery.dao.entity.OrderItemEntity;
import com.samuela.grocery.dao.entity.PaymentEntity;
import com.samuela.grocery.dao.entity.ProductEntity;
import com.samuela.grocery.dto.OrderRequest;
import com.samuela.grocery.dto.OrderItemRequest;
import com.samuela.grocery.exception.InvalidInputException;
import com.samuela.grocery.exception.ResourceNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private PaymentDao paymentDao;

    // Create a new order
    // Since placing order is a transaction, use @Transactional
    @Transactional
    public OrderEntity createOrder(OrderRequest request) {
        CustomerEntity customer = customerDao.findByEmail(request.getCustomerEmail());

        OrderEntity order = new OrderEntity();
        order.setCustomer(customerDao.findById(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + request.getCustomerEmail())));
        
        PaymentEntity payment = paymentDao.findById(request.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + request.getPaymentId()));
        order.setPaymentId(payment);
        
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderDate(LocalDateTime.now());

        List<OrderItemEntity> items = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            ProductEntity product = productDao.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + itemRequest.getProductId()));

            // Check if enough stock is available
            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new InvalidInputException(
                    "Not enough stock for product: " + product.getProductName()
                );
            }

            // Reduce product quantity
            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productDao.save(product); // save updated product stock

            // Create order item
            OrderItemEntity item = new OrderItemEntity();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setProductPrice(itemRequest.getProductPrice());
            items.add(item);
        }

        order.setOrderItems(items);
        return orderDao.save(order);
    }

    // Get all orders
    public List<OrderEntity> getAllOrders() {
        return orderDao.findAll();
    }

    // Get order by ID
    public Optional<OrderEntity> getOrderById(int id) {
        return orderDao.findById(id);
    }

    // Get all orders for a customer
    public List<OrderEntity> getOrdersByCustomerId(int customerId) {
        return orderDao.findByCustomerCustomerId(customerId);
    }

    // Update order status
    public OrderEntity updateOrderStatus(int orderId, String orderStatus) {
        // Find the order by ID
        OrderEntity order = orderDao.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));

        // Convert string to enum and validate (because entity uses enum)
        OrderEntity.OrderStatus newOrderStatus;
        try {
        	newOrderStatus = OrderEntity.OrderStatus.valueOf(orderStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid order status: " + orderStatus);
        }

        // Set the new status and save
        order.setOrderStatus(newOrderStatus);
        return orderDao.save(order);
    }


    // Delete order by ID
    public void deleteOrder(int id) {
        if (!orderDao.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id " + id);
        }
        orderDao.deleteById(id);
    }
    
    public List<OrderEntity> getOrdersByCustomerEmail(String email) {
        return orderDao.findByCustomerEmail(email);
    }

}
