package com.targa.labs.myBoutique.order.service;

import com.targa.labs.myBoutique.commons.dto.AddressDto;
import com.targa.labs.myBoutique.order.domain.Address;

public class AddressService {
	public static AddressDto mapToDto(Address address) {
		if (address != null) {
			return new AddressDto(
					address.getAddress1(),
					address.getAddress2(),
					address.getCity(),
					address.getPostcode(),
					address.getCountry());
		}
		return null;
	}

}
