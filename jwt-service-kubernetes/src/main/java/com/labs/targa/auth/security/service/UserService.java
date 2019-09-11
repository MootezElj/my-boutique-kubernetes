package com.labs.targa.auth.security.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labs.targa.auth.security.UserPrincipal;
import com.labs.targa.auth.security.domain.User;
import com.labs.targa.auth.security.repository.UserRepository;

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
	
	public String getUserAuthoritiesByUsername(String username) {
		if (username!=null) {
			User user = this.userRepository.findByUsername(username);
			UserPrincipal principal = new UserPrincipal(user);
			System.out.println(principal.getAuthorities().toString());
			return principal.getAuthorities().toString();
		}
		return null;
	}
	
	public User getUserByUsername(String username) {
		if (username!=null) {
			User user = this.userRepository.findByUsername(username);
			return user;
		}
		return null;
	}
	
	
	

}
