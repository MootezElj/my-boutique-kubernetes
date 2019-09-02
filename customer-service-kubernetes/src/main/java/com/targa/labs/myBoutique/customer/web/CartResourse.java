package com.targa.labs.myBoutique.customer.web;

import com.targa.labs.myBoutique.commons.dto.CartDto;
import com.targa.labs.myBoutique.customer.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.targa.labs.myBoutique.commons.utils.Web.API;

@RequiredArgsConstructor
@RestController
@RequestMapping(API + "/carts")
public class CartResourse {
	private final CartService cartService;
	@GetMapping("/")
	public List<CartDto> findAll() {
		return this.cartService.findAll();
	}
	@GetMapping("/active/")
	public List<CartDto> findAllActiveCarts() {
		return this.cartService.findAllActiveCarts();
	}
	@GetMapping("/customer/{id}/")
	public CartDto getActiveCartForCustomer(@PathVariable("id") Long customerId) {
		return this.cartService.getActiveCart(customerId);
	}
	@GetMapping("/{id}/")
	public CartDto findById(@PathVariable Long id) {
		return this.cartService.findById(id);
	}
	@PostMapping("/customer/{id}/")
	public CartDto create(@PathVariable("id") Long customerId) {
		return this.cartService.create(customerId);
	}
	@DeleteMapping("/{id}/")
	public void delete(@PathVariable Long id) {
		this.cartService.delete(id);
	}
}