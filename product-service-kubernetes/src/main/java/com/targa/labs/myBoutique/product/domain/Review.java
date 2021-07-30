package com.targa.labs.myBoutique.product.domain;


import com.targa.labs.myBoutique.commons.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "review")
public class Review extends AbstractEntity {
	
	@NotNull
	@Column(name = "title", nullable = false)
	private String title;
	
	@NotNull
	@Column(name = "description", nullable = false)
	private String description;
	
	@NotNull
	@Column(name = "rating", nullable = false)
	private Long rating;

}
