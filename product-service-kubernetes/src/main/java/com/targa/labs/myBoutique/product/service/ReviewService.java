package com.targa.labs.myBoutique.product.service;


import com.targa.labs.myBoutique.commons.dto.ReviewDto;
import com.targa.labs.myBoutique.product.domain.Review;
import com.targa.labs.myBoutique.product.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@CrossOrigin
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	
	
	public List<ReviewDto> findAll(){
		log.debug("Request to find all Reviews : {}");
		return this.reviewRepository
				.findAll()
				.stream()
				.map(ReviewService::mapToDto)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public ReviewDto findById(Long id) {
		log.debug("Request to find Review by Id: {}", id);
		return this.reviewRepository.findById(id)
				.map(ReviewService::mapToDto)
				.orElse(null);
	}
	
	public ReviewDto create(ReviewDto reviewDto) {
		return mapToDto(this.reviewRepository.save(
				new Review(
						reviewDto.getTitle(),
						reviewDto.getDescription(),
						reviewDto.getRating())));
		
	}
	
	public void delete(Long id) {
		log.debug("Request to delete a review :{}", id);
		this.reviewRepository.deleteById(id);
	}
	
	
	public static ReviewDto mapToDto(Review review) {
		if (review != null) {
			return new ReviewDto(
					review.getId(),
					review.getTitle(),
					review.getDescription(),
					review.getRating());
		}
		return null;
	}

}
