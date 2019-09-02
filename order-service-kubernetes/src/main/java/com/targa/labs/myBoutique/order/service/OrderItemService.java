package com.targa.labs.myBoutique.order.service;

import com.targa.labs.myBoutique.commons.dto.OrderItemDto;
import com.targa.labs.myBoutique.order.domain.Order;
import com.targa.labs.myBoutique.order.domain.OrderItem;
import com.targa.labs.myBoutique.order.repository.OrderItemRepository;
import com.targa.labs.myBoutique.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class OrderItemService {

	private final OrderItemRepository orderItemRepository;
	private final OrderRepository orderRepository;

	public List<OrderItemDto> findAll(){
		log.debug("Request to find all OrderItems");
		return this.orderItemRepository.findAll()
				.stream()
				.map(OrderItemService::mapToDto)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public OrderItemDto findById(Long id) {
		return this.orderItemRepository.findById(id)
				.map(OrderItemService::mapToDto)
				.orElse(null);
	}


	public OrderItemDto create(OrderItemDto orderItemDto) {
		log.debug("Request to create OrderItem : {}", orderItemDto);
		Order order = this.orderRepository.findById(orderItemDto.getOrderId())
				.orElseThrow(() -> new IllegalStateException("The Order does not exist!"));
			return mapToDto(
				this.orderItemRepository.save(
						new OrderItem(
								orderItemDto.getQuantity(),
								orderItemDto.getProductId(),
								order
								)));
	}

	public void delete(Long id) {
		log.debug("Request to delete OrderItem:{}", id);
		this.orderItemRepository.deleteById(id);
	}


	public static OrderItemDto mapToDto(OrderItem orderItem) {
		if (orderItem != null) {
			return new OrderItemDto(
					orderItem.getId(),
					orderItem.getQuantity(),
					orderItem.getProductId(),
					orderItem.getOrder().getId());
		}

		return null;
	}




}
