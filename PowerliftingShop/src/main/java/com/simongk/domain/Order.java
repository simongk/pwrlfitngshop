package com.simongk.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "id")
public class Order {

	@Id
	@GeneratedValue
	private long id;

	private String firstName;
	private String lastName;

	private String street;
	private String city;
	private String postCode;
	private String emailAddress;

	private BigDecimal totalCost;



}
