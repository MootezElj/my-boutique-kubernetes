package com.targa.labs.myBoutique.customer.repository;

import com.targa.labs.myBoutique.customer.domain.Cart;
import com.targa.labs.myBoutique.customer.domain.enumeration.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	List<Cart> findByStatus(CartStatus status);
	List<Cart> findByStatusAndCustomerId(CartStatus status, Long customerId);
	Optional<Cart> findById(Long id);

}
