package com.targa.labs.myBoutique.customer.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.targa.labs.myBoutique.customer.security.domain.User;



public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
