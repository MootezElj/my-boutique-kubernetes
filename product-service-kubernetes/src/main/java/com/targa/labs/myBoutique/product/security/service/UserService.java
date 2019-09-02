package com.targa.labs.myBoutique.product.security.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.targa.labs.myBoutique.product.security.domain.User;
import com.targa.labs.myBoutique.product.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
	
	
	private final UserRepository userRepository;
	
	public List<User> getAllUsers() {
		log.debug("request to find all users");
		return userRepository.findAll();
	}
	
	public User findUserById(Long id){
		log.debug("request to find user by Id: {} "+ id);
		return userRepository.findById(id).orElse(null);
	}
	
	public User addUser(User user) {
		log.debug("Request to add a new user:{} "+ user);
		return this.userRepository.save(user);
	}
	
	public void deleteUser(Long id) {
		log.debug("Request to delete user with id :{} "+id);
		if (id == null || this.userRepository.findById(id)==null) {
			throw(new  IllegalArgumentException("invalid id"));
		}
		 this.userRepository.deleteById(id);

	}
	

}
