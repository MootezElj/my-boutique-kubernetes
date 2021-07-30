package com.targa.labs.myBoutique.product.service;

import com.targa.labs.myBoutique.commons.dto.*;
import com.targa.labs.myBoutique.product.domain.Category;
import com.targa.labs.myBoutique.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@CrossOrigin
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public List<CategoryDto> findAll() {
		log.debug("Request to find all Categories");
		return this.categoryRepository.findAll().stream().map(CategoryService::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDto findById(Long id) {
		return this.categoryRepository.findById(id).map(CategoryService::mapToDto)
				.orElseThrow(IllegalStateException::new);
	}

	public CategoryDto create(CategoryDto categoryDto) {
		log.debug("Request to create Category : {}", categoryDto);
		return mapToDto(this.categoryRepository
				.save(new Category(categoryDto.getName(), categoryDto.getDescription(), Collections.emptySet())));
	}

	public CategoryDto update(CategoryDto categoryDto) {
		log.debug("Request to create Category : {}", categoryDto);

		Optional<Category> category = this.categoryRepository.findById(categoryDto.getId());
		if (category != null) {
			category.get().setName(categoryDto.getName());
			category.get().setDescription(categoryDto.getDescription());
			return mapToDto(this.categoryRepository.save(category.get()));
		}
		return null;
	}

	public void delete(Long id) {
		log.debug("Request to delete Category : {}", id);
		this.categoryRepository.deleteById(id);
	}

	public static CategoryDto mapToDto(Category category) {
		if (category != null) {
			return new CategoryDto(category.getId(), category.getName(), category.getDescription());
		}
		return null;
	}
}
