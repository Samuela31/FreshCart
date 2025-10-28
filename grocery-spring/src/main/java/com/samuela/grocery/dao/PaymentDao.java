package com.samuela.grocery.dao;

import com.samuela.grocery.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDao extends JpaRepository<PaymentEntity, Integer> {

    // Find all successful payments
    List<PaymentEntity> findByStatus(PaymentEntity.PaymentStatus status);

    // Find all payments by cardholder name
    List<PaymentEntity> findByCardholderNameIgnoreCase(String cardholderName);
}
