package com.targa.labs.myBoutique.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.targa.labs.myBoutique.commons.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractEntity {

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotNull
	@Column(name = "description", nullable = false)
	private String description;
	
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private Set<Product> products = new HashSet<>();
}
