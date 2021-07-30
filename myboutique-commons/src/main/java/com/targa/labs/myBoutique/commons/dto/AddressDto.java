package com.targa.labs.myBoutique.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDto {
	private String address1;
	private String address2;
	private String city;
	private String postcode;
	private String country;
}
