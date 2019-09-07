package com.targa.labs.myBoutique.product.security.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	private Boolean active = true ;

	private String roles = "";

	private String permissions = "";
	
	
//	public List<String> getRoleList(){
//		if (this.roles.length()>0) {
//			return Arrays.asList(this.roles.split(","));
//		}
//		return new ArrayList<>();
//	}
//	
//	public List<String> getPermissionList(){
//		if (this.roles.length()>0) {
//			return Arrays.asList(this.permissions.split(","));
//		}
//		return new ArrayList<>();
//	}

	public User(String username, String password,  String roles, String permissions) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.permissions = permissions;
	}


}
