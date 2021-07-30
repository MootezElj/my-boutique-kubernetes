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

import com.targa.labs.myBoutique.product.domain.Category;
import com.targa.labs.myBoutique.product.repository.CategoryRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCircuitBreaker
@EnableSwagger2
@SpringBootApplication
public class ProductServiceApplication implements CommandLineRunner{
	

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@Autowired
	private CategoryRepository categoryRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {
        
    	Category musiqueInstrument = new Category("Music Instrument", "music instrument");
    	
    	Category footBall = new Category("Football t-shirt", "Footbal t-shirt desc");
    	
    	Category pcVideoGames = new Category("PC Video Games", "Video games pc department");
    	
    	Category ps4VideoGames = new Category("PS4 Video Games", "Video games PS4 department");
    	
    	Category pcSoftware = new Category("Pc software", "Pc Software department");
    	
    	Category healthCare = new Category("Health Care", "Health Care department");
    	
       List<Category> categories = Arrays.asList(pcSoftware,healthCare,musiqueInstrument,footBall,pcVideoGames,ps4VideoGames);
        
       this.categoryRepository.saveAll(categories);
	}

}
