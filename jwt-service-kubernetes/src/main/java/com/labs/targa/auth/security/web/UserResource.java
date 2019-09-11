package com.labs.targa.auth.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labs.targa.auth.security.domain.User;
import com.labs.targa.auth.security.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jwt")
public class UserResource {

	private final UserService userService;

	@GetMapping("/id/{username}/")
	public String findById(@PathVariable String username) {
		return this.userService.getUserAuthoritiesByUsername(username);
	}

	@GetMapping("/id/{id}/")
	public User getUserById(@PathVariable Long id) {
		User user = this.userService.findUserById(id);
		return user;
	}

	@GetMapping("/{username}/")
	public User getUserByUsername(@PathVariable String username) {
		
		if (this.userService.getUserByUsername(username) != null) {
			User user = this.userService.getUserByUsername(username);
			System.out.println(user);
			return user;
		}
		return null;
	}

}
