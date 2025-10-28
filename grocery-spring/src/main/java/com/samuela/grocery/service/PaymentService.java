package com.samuela.grocery.service;

import com.samuela.grocery.dao.PaymentDao;
import com.samuela.grocery.dao.entity.PaymentEntity;
import com.samuela.grocery.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentDao paymentDao;

    /**
     * Process a new payment transaction.
     * Validates card details, dummy CVV, and OTP.
     * Saves payment record in DB.
     */
    public PaymentEntity processPayment(PaymentEntity payment) {
        logger.info("Processing payment for Order... ");

        // Basic dummy CVV and OTP validation
        if (!isValidCvv(payment.getCvv())) {
            logger.warn("Payment failed: Invalid CVV!");
            payment.setStatus(PaymentEntity.PaymentStatus.FAILED);
            return paymentDao.save(payment);
        }

        if (!isValidOtp(payment.getOtp())) {
            logger.warn("Payment failed: Invalid OTP!");
            payment.setStatus(PaymentEntity.PaymentStatus.FAILED);
            return paymentDao.save(payment);
        }

        // Mark payment as successful
        payment.setStatus(PaymentEntity.PaymentStatus.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());
        PaymentEntity savedPayment = paymentDao.save(payment);

        logger.info("Payment successful for Order with Payment ID: {}", 
                savedPayment.getId());
        return savedPayment;
    }

    // Fetch all payments.
    public List<PaymentEntity> getAllPayments() {
        logger.info("Fetching all payments...");
        return paymentDao.findAll();
    }

    // Fetch payment by ID.
    public PaymentEntity getPaymentById(int id) {
        logger.info("Fetching payment with ID: {}", id);
        return paymentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
    }

    // Dummy CVV validation
    private boolean isValidCvv(String cvv) {
        return cvv != null && cvv.matches("^[0-9]{3}$");
    }

    // Dummy OTP validation
    private boolean isValidOtp(String otp) {
        return otp != null && otp.matches("^[0-9]{6}$");
    }
}
