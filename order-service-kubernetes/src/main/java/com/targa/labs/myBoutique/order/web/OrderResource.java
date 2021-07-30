package com.targa.labs.myBoutique.order.web;

import com.targa.labs.myBoutique.commons.dto.CartDto;
import com.targa.labs.myBoutique.commons.dto.OrderDto;
import com.targa.labs.myBoutique.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.targa.labs.myBoutique.commons.utils.Web.API;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + "/orders")
public class OrderResource {
	private final OrderService orderService;

	@GetMapping("/")
	public List<OrderDto> findAll() {
		return this.orderService.findAll();
	}
	
	
	@GetMapping("/{id}/")
	public OrderDto findById(@PathVariable Long id) {return this.orderService.findById(id);
	}
	
	@DeleteMapping("/{id}/")
	public void delete(@PathVariable Long id) {
		this.orderService.delete(id);
	}

	@PostMapping("/")
	public void create(CartDto cartDto){
		 this.orderService.create(cartDto);
	}
}
