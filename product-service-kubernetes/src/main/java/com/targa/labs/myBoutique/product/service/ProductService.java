package com.targa.labs.myBoutique.product.service;

import com.targa.labs.myBoutique.commons.dto.ProductDto;
import com.targa.labs.myBoutique.product.domain.Product;
import com.targa.labs.myBoutique.product.domain.enmeration.ProductStatus;
import com.targa.labs.myBoutique.product.repository.CategoryRepository;
import com.targa.labs.myBoutique.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	public List<ProductDto> findAll(){
		log.debug("Request to find all Products:{} ");
		return this.productRepository.findAll()
				.stream().map(ProductService::mapToDto)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ProductDto findById(Long id) {
		log.debug("Request to find Product By Id:{} ",id);
		return this.productRepository.findById(id)
				.map(ProductService::mapToDto).orElse(null);
	}

	public ProductDto create(ProductDto productDto) {
		return mapToDto(this.productRepository.save(
				new Product(
						productDto.getName(),
						productDto.getDescription(),
						productDto.getPrice(),
						productDto.getQuantity(),
						ProductStatus.valueOf(productDto.getStatus()),
						productDto.getSalesCounter(),
						null,
						this.categoryRepository.findById(productDto.getCategory().getId())
						.orElse(null)

						)
				));

	}
	
	public void delete(Long id) {
		log.debug("Request to delete Product : {}", id);
		this.productRepository.deleteById(id);
		}

	public static ProductDto mapToDto(Product product) {
		if (product != null) {
			return new ProductDto(
					product.getId(),
					product.getName(),
					product.getDescription(),
					product.getPrice(),
					product.getQuantity(),
					product.getStatus().toString(),
					product.getSalesCounter(),
					CategoryService.mapToDto(product.getCategory()));
		}
		return null;
	}

}
