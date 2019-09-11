package com.labs.targa.auth.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labs.targa.auth.security.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
