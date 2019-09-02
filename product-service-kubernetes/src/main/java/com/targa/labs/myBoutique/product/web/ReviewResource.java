package com.targa.labs.myBoutique.product.web;


import com.targa.labs.myBoutique.commons.dto.ReviewDto;
import com.targa.labs.myBoutique.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.targa.labs.myBoutique.commons.utils.Web.API;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + "/reviews")
public class ReviewResource {
	private final ReviewService reviewService;
	
	@GetMapping("/")
	public List<ReviewDto> findAll(){
		return this.reviewService.findAll();
	}
	
	@GetMapping("/{id}/")
	public ReviewDto findById(@PathVariable Long id) {
		return this.reviewService.findById(id);
	}
	
	@PostMapping("/")
	public ReviewDto create(ReviewDto reviewDto) {
		return this.reviewService.create(reviewDto);
	}
	
	
	@DeleteMapping("/{id}/")
	public void delete(@PathVariable Long id) {
		 this.reviewService.findById(id);
	}
	
	
	
}
