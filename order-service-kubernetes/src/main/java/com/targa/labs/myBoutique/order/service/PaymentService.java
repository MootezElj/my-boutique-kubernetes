package com.targa.labs.myBoutique.order.service;

import com.targa.labs.myBoutique.commons.dto.PaymentDto;
import com.targa.labs.myBoutique.order.domain.Order;
import com.targa.labs.myBoutique.order.domain.Payment;
import com.targa.labs.myBoutique.order.domain.enumeration.PaymentStatus;
import com.targa.labs.myBoutique.order.repository.OrderRepository;
import com.targa.labs.myBoutique.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;

	public List<PaymentDto> findAll() {
		log.debug("Request to get all Payments");
		return this.paymentRepository.findAll()
				.stream()
				.map(PaymentService::mapToDto)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public PaymentDto findById(Long id) {
		return this.paymentRepository.findById(id)
				.map(PaymentService::mapToDto)
				.orElse(null);
	}

	public PaymentDto create(PaymentDto paymentDto) {
		log.debug("Request to create payment: {}",paymentDto);
		Order order = this.orderRepository.findById(paymentDto.getOrderId())
				.orElseThrow(()->new IllegalStateException("Order does not exist!"));
		return mapToDto(this.paymentRepository
				.save(new Payment(
						paymentDto.getPaypalPaymentId(),
						PaymentStatus.valueOf(paymentDto.getStatus()),
						order)));
	}

	public void delete(Long id) {
		log.debug("Request to delete Payment : {}", id);
		this.paymentRepository.deleteById(id);
	}

	public static PaymentDto mapToDto(Payment payment) {
		if (payment !=null)
			return new PaymentDto(
					payment.getId(),
					payment.getPaypalPaymentId(),
					payment.getStatus().toString(),
					payment.getOrder().getId());
		return null;
	}
}
