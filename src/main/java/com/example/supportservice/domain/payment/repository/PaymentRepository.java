package com.example.supportservice.domain.payment.repository;

import com.example.supportservice.domain.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
