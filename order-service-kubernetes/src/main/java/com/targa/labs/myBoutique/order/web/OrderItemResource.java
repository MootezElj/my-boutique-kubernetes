package com.targa.labs.myBoutique.order.web;

import com.targa.labs.myBoutique.commons.dto.OrderItemDto;
import com.targa.labs.myBoutique.order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.targa.labs.myBoutique.commons.utils.Web.API;

@RestController
@RequestMapping(API + "/order-items")
@RequiredArgsConstructor
public class OrderItemResource {

	private final OrderItemService orderItemService;
	
	@GetMapping("/")
	public List<OrderItemDto> findAll(){
		return this.orderItemService.findAll();
	}
	
	@GetMapping("/{id}/")
	public OrderItemDto findById(@PathVariable Long id) {
		return this.orderItemService.findById(id);
	}
	@PostMapping("/")
	public OrderItemDto create(OrderItemDto orderItemDto) {
		return this.orderItemService.create(orderItemDto);
	}
	
	@DeleteMapping("/{id}/")
	public void delete(@PathVariable Long id) {
		this.orderItemService.delete(id);
	}
	
}
