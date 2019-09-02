package com.targa.labs.myBoutique.order.domain;

import com.targa.labs.myBoutique.commons.domain.AbstractEntity;
import com.targa.labs.myBoutique.order.domain.enumeration.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="payment")
public class Payment extends AbstractEntity{

	@Column(name = "paypal_payment_id")
	private String paypalPaymentId;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus status;

	@OneToOne
	@JoinColumn(unique = true)
	private Order order;
}
