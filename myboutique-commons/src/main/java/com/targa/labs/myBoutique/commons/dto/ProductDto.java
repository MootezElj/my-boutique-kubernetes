package com.targa.labs.myBoutique.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Integer quantity;
	private String status;
	private Integer salesCounter;
	private CategoryDto categoryDto;
}
