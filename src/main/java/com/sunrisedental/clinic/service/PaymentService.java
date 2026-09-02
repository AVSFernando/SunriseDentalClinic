package com.sunrisedental.clinic.service;

import com.sunrisedental.clinic.entity.Payment;
import com.sunrisedental.clinic.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Save payment
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Get payment by ID
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    // Delete payment
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}