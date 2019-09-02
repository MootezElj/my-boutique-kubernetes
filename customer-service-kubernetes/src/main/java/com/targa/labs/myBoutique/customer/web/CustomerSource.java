package com.targa.labs.myBoutique.customer.web;

import com.targa.labs.myBoutique.commons.dto.CustomerDto;
import com.targa.labs.myBoutique.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.targa.labs.myBoutique.commons.utils.Web.API;

@RestController
@RequestMapping(API + "/customers")
@RequiredArgsConstructor
public class CustomerSource {

	private final CustomerService customerService;

	@GetMapping("/")
	public List<CustomerDto> findAll() {
		return this.customerService.findAll();
	}

	@GetMapping("/{id}/")
	public CustomerDto findById(@PathVariable Long id) {
		return this.customerService.findById(id);
	}

	@GetMapping("/active/")
	public List<CustomerDto> findAllActive() {
		return this.customerService.findAllActive();
	}
	@GetMapping("/inactive/")
	public List<CustomerDto> findAllInactive() {
		return this.customerService.findAllInactive();
	}

	@PostMapping("/")
	public CustomerDto create(@RequestBody CustomerDto customerDto) {
		return this.customerService.create(customerDto);
	}
	@DeleteMapping("/{id}/")
	public void delete(@PathVariable Long id) {
		this.customerService.delete(id);
	}

}
