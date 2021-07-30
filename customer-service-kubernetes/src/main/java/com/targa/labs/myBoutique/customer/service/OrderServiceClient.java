package com.targa.labs.myBoutique.customer.service;


import com.targa.labs.myBoutique.commons.dto.CartDto;
import com.targa.labs.myBoutique.commons.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "order-service",url = "http://order-service")
public interface OrderServiceClient {

    @RequestMapping(value = "/api/orders", method = POST)
    OrderDto create(CartDto cartDto);

}