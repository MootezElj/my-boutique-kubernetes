package com.targa.labs.myBoutique.order.repository;

import com.targa.labs.myBoutique.order.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
	Optional<Payment> findById(Long id);
}
