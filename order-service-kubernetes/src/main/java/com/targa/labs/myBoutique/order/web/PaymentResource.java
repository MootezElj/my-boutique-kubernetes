package com.targa.labs.myBoutique.order.web;

import com.targa.labs.myBoutique.commons.dto.PaymentDto;
import com.targa.labs.myBoutique.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.targa.labs.myBoutique.commons.utils.Web.API;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + "/payments")
public class PaymentResource {
	private final PaymentService paymentService;
	
	@GetMapping("/")
	public List<PaymentDto> findAll(){
		return this.paymentService.findAll();
	}
	
	@GetMapping("/{id}/")
	public PaymentDto findById(@PathVariable Long id){
		return this.paymentService.findById(id);
	}
	
	@PostMapping("/")
	public PaymentDto create(PaymentDto paymentDto) {
		return this.create(paymentDto);
	}
	
	@DeleteMapping("/{id}/")
	public void delete(@PathVariable Long id){
		 this.paymentService.delete(id);
	}

}
