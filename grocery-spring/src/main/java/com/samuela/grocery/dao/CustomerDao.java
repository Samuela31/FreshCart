package com.samuela.grocery.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samuela.grocery.dao.entity.CustomerEntity;

@Repository
public interface CustomerDao extends JpaRepository<CustomerEntity, Integer> {
	
	// Search customer by email 
	CustomerEntity findByEmail(String email);


}
