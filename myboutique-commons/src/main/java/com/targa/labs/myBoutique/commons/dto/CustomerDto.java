package com.targa.labs.myBoutique.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {
private Long id;
private String firstName;
private String lastName;
private String email;
private String telephone;
}