package com.targa.labs.myBoutique.customer.repository;

import com.targa.labs.myBoutique.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	List<Customer> findAllByEnabled(boolean enabled);
	Optional<Customer> findById(Long id);
}
