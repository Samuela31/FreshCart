package com.samuela.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samuela.grocery.dao.CustomerDao;
import com.samuela.grocery.dao.RoleDetailsDao;
import com.samuela.grocery.dao.UserInfoDao;
import com.samuela.grocery.dao.entity.CustomerEntity;
import com.samuela.grocery.dao.entity.RoleDetails;
import com.samuela.grocery.dao.entity.UserInfo;
import com.samuela.grocery.exception.ResourceNotFoundException;


@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private RoleDetailsDao roleDetailsDao;

    @Autowired
    private PasswordEncoder passwordEncoder; // Spring Security's encoder

    // Create a new customer and give them CUSTOMER role (basically sign-up)
    public CustomerEntity createCustomer(CustomerEntity customer) {
    	//Always encrypt password
    	customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        // Find the CUSTOMER role
        RoleDetails customerRole = roleDetailsDao.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new ResourceNotFoundException("Role CUSTOMER not found in DB!"));

    	// Save the customer in the customers table only if password encryption and role is successful
        // Otherwise customer would get saved without entry in UserInfo table if role is not found
        CustomerEntity savedCustomer = customerDao.save(customer);
        
        // Create corresponding user record in users table
        UserInfo user = new UserInfo();
        user.setEmail(savedCustomer.getEmail());
        user.setPassword(savedCustomer.getPassword());
        user.setAllRoles(List.of(customerRole));
        userInfoDao.save(user);

        return savedCustomer;
    }

    // Get all customers
    public List<CustomerEntity> getAllCustomers() {
        return customerDao.findAll();
    }

    // Get customer by ID
    public Optional<CustomerEntity> getCustomerById(int id) {
        return customerDao.findById(id);
    }

    // Update customer
    public CustomerEntity updateCustomer(int id, CustomerEntity updatedCustomer) {
        return customerDao.findById(id).map(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setPassword(updatedCustomer.getPassword());
            return customerDao.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    // Delete customer by ID
    public void deleteCustomer(int id) {
        if (!customerDao.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id " + id);
        }
        customerDao.deleteById(id);
    }
}
