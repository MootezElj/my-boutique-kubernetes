package com.targa.labs.myBoutique;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.targa.labs.myBoutique.customer.domain.Cart;
import com.targa.labs.myBoutique.customer.domain.Customer;
import com.targa.labs.myBoutique.customer.repository.CartRepository;
import com.targa.labs.myBoutique.customer.repository.CustomerRepository;
import com.targa.labs.myBoutique.customer.security.domain.User;
import com.targa.labs.myBoutique.customer.security.repository.UserRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableCircuitBreaker
@SpringBootApplication
@EnableFeignClients
public class CustomerServiceApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CustomerRepository customerRepository;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
//		// Delete all
//        this.userRepository.deleteAll();

        // Crete users
        User dan = new User("customer",passwordEncoder.encode("customer13"),"USER","ACCESS_TEST1,ACCESS_TEST2");
        User admin = new User("customerAdmin",passwordEncoder.encode("admin123"),"ADMIN","ACCESS_TEST1,ACCESS_TEST2");
        User manager = new User("manager",passwordEncoder.encode("manager123"),"MANAGER","ACCESS_TEST1");

        Customer mootez = new Customer("Mootez", "Elj", "eljmootez@gmail.com", "51906322", Collections.emptySet());
        Customer mootez2 = new Customer("Mootez", "Elj", "eljmootez@gmail.com", "51906322", Collections.emptySet());
        mootez2.setEnabled(false);
        Cart c1= new Cart(mootez);
        
        this.customerRepository.save(mootez);
        this.customerRepository.save(mootez2);
        this.cartRepository.save(c1);
        
        List<User> users = Arrays.asList(dan,admin,manager);

        // Save to db
        System.out.println("Saving users To DB");
        this.userRepository.saveAll(users);
		
	}
}
