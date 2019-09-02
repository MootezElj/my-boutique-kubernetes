package com.targa.labs.myBoutique.product.web;


import com.targa.labs.myBoutique.commons.dto.CategoryDto;
import com.targa.labs.myBoutique.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.targa.labs.myBoutique.commons.utils.Web.API;


@RequiredArgsConstructor
@RestController
@RequestMapping(API + "/categories")
public class CategoryResource {

	private final CategoryService categoryService;

	@GetMapping("/")
	public List<CategoryDto> getAll(){
		return this.categoryService.findAll();
	}

	@GetMapping("/{id}/")
	public CategoryDto findById(@PathVariable Long id) {
		return this.categoryService.findById(id);		
	}

	@PostMapping("/")
	public CategoryDto create(@RequestBody CategoryDto categoryDto) {
		return this.categoryService.create(categoryDto);
	}

		
	@PutMapping("/")
	public CategoryDto update(@RequestBody CategoryDto categoryDto) {
		
		return this.categoryService.update(categoryDto);
	}
	
	@DeleteMapping("/{id}/")
	public void delete(@PathVariable Long id) {
		this.categoryService.delete(id);
	}

}
