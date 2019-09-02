package com.targa.labs.myBoutique;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.targa.labs.myBoutique.order.security.domain.User;
import com.targa.labs.myBoutique.order.security.repository.UserRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableCircuitBreaker
@SpringBootApplication
public class OrderServiceApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
//		// Delete all
//        this.userRepository.deleteAll();

        // Crete users
        User dan = new User("user",passwordEncoder.encode("user"),"USER","ACCESS_TEST1,ACCESS_TEST2");
        User admin = new User("customerAdmin",passwordEncoder.encode("admin123"),"ADMIN","ACCESS_TEST1,ACCESS_TEST2");
        User manager = new User("manager",passwordEncoder.encode("manager123"),"MANAGER","ACCESS_TEST1");


        

        
        List<User> users = Arrays.asList(dan,admin,manager);

        // Save to db
        System.out.println("Saving users To DB");
        this.userRepository.saveAll(users);
		
	}

}
