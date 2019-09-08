package com.labs.targa.auth;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.labs.targa.auth.security.domain.User;
import com.labs.targa.auth.security.repository.UserRepository;

@SpringBootApplication
public class JwtServiceApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public static void main(String[] args) {
		SpringApplication.run(JwtServiceApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
//		// Delete all
//        this.userRepository.deleteAll();

        // Crete users
        User dan = new User("user",passwordEncoder.encode("user"),"USER","");
        User admin = new User("admin",passwordEncoder.encode("admin123"),"ADMIN","ACCESS_TEST1,ACCESS_TEST2");
        User manager = new User("manager",passwordEncoder.encode("manager123"),"MANAGER","ACCESS_TEST1");

        List<User> users = Arrays.asList(dan,admin,manager);

        // Save to db
        System.out.println("Saving users To DB");
        this.userRepository.saveAll(users);
		
	}

}
