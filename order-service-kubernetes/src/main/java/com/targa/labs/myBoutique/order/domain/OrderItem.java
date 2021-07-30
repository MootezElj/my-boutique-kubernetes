package com.targa.labs.myBoutique.order.domain;

import com.targa.labs.myBoutique.commons.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "order_item")
public class OrderItem extends AbstractEntity{
	
	@NotNull
	@Column(nullable = false)
	private Long quantity;
	
	
	private Long productId;
	
	@ManyToOne
	private Order order;
	
	
}
