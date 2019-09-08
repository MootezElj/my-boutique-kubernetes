package com.targa.labs.myBoutique.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderItemDto {
	private Long id;
	private Long quantity;
	private Long productId;
	private Long orderId;
}
