package com.targa.labs.myBoutique.order.repository;

import com.targa.labs.myBoutique.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>{
	Optional<Order> findById(Long id);
}
