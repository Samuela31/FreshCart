package com.samuela.grocery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samuela.grocery.dao.entity.OrderEntity;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Integer> {

	// To get all orders for a customer
	List<OrderEntity> findByCustomerCustomerId(int customerId);
	
	List<OrderEntity> findByCustomerEmail(String email);
}
