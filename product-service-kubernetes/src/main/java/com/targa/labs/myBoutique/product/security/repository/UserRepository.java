package com.targa.labs.myBoutique.product.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.targa.labs.myBoutique.product.security.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
