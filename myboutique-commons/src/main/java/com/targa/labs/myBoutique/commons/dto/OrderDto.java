package com.targa.labs.myBoutique.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class OrderDto {
private Long id;
private BigDecimal totalPrice;
private String status;
private ZonedDateTime shipped;
private PaymentDto payment;
private AddressDto shipmentAddress;
}