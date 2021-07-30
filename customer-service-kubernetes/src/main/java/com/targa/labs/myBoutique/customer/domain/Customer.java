package com.targa.labs.myBoutique.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.targa.labs.myBoutique.commons.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<Cart> carts;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

	public Customer(String firstName, String lastName, @Email String email, String telephone, Set<Cart> carts) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telephone = telephone;
		this.carts = carts;
		this.enabled = true;
	}
    
    

}