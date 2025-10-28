package com.samuela.grocery.dao.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int orderItemId;

    // Many OrderItems belong to One Order
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderEntity order;

    // Many OrderItems refer to One Product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "quantity")
    @NotNull(message = "Quantity can't be null!")
    @Positive(message = "Product quantity must be greater than 0!")
    private int quantity;

    @Column(name = "product_price", precision = 10, scale = 2)
    @NotNull(message = "Product price can't be null!")
    @Positive(message = "Product price must be greater than 0!")
    private BigDecimal productPrice;

    // subtotal = quantity * productPrice
    @Column(name = "subtotal", precision = 10, scale = 2)
    @NotNull(message = "Subtotal can't be null!")
    @Positive(message = "Subtotal must be greater than 0!")
    private BigDecimal subtotal;

    // Helper method 
    @PrePersist
    @PreUpdate
    private void calculateSubtotal() {
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}
