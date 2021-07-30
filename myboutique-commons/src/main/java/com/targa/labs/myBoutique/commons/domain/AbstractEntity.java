package com.targa.labs.myBoutique.commons.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@CreatedDate
	@Column(name = "created_date", nullable = false)
	@JsonIgnore
	private Instant createDate = Instant.now();
	
	@CreatedDate
	@Column(name = "last_modified_date")
	@JsonIgnore
	private Instant lastModified = Instant.now();
	
	
}
