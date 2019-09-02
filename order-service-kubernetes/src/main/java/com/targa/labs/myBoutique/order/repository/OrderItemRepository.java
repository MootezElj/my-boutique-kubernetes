package com.targa.labs.myBoutique.order.repository;

import com.targa.labs.myBoutique.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	Optional<OrderItem> findById(Long id);
}
