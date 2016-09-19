package com.simongk.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"product_id"}))
@Getter
@Setter
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int quantity;
	private BigDecimal cartCost;

	@OneToOne
	private Product product;

}
