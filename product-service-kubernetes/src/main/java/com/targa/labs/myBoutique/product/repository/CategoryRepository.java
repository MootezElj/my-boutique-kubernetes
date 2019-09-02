package com.targa.labs.myBoutique.product.repository;

import com.targa.labs.myBoutique.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	Optional<Category> findById(Long id);
	void deleteById(Long id);

}
