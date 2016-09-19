package com.simongk.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Entity
@Getter
@Setter
@Builder
public class Product {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;
	private String company;
	private BigDecimal cost;
	
	@Tolerate
	public Product(){}

}
