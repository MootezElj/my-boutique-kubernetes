package com.targa.labs.myBoutique.customer.domain;

import com.targa.labs.myBoutique.commons.domain.AbstractEntity;
import com.targa.labs.myBoutique.customer.domain.enumeration.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "cart")
public class Cart extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @ManyToOne
    private Customer customer;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    public Cart(Customer customer) {
        this.customer = customer;
        this.status = CartStatus.NEW;
    }

    public Cart(Customer customer, CartStatus status) {
        this.customer = customer;
        this.status = status;
    }
}