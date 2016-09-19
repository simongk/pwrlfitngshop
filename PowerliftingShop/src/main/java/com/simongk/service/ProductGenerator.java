package com.simongk.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.simongk.domain.Product;

import lombok.Getter;

@Service
public class ProductGenerator {

	private final String[] names = { "Knee Sleeves", "Belt", "Accessory" };
	private final String[] descriptions = { "Solid piece of equipment", "Worth it's price", "Get it to boost your progress" };
	private final String[] companies = { "Titan", "SBD", "Metal", "Pionier", "Inzer", "Power System" };

	@Getter
	private List<String> productNames;
	@Getter
	private List<String> productDescriptions;
	@Getter
	private List<String> productCompanies;
	private static final double COST_BASE = 300.0;
	private static final double COST_SPREAD = 500.0;
	private final Random random = new Random();

	public Product generate() {
		return Product.builder().name(getRandomName()).description(getRandomDescription()).company(getRandomCompany())
				.cost(getRandomCost()).build();
	}

	private String getRandomCompany() {
		productCompanies = Arrays.asList(companies);
		return getRandom(getProductCompanies());
	}

	private String getRandomDescription() {
		productDescriptions = Arrays.asList(descriptions);
		return getRandom(getProductDescriptions());
	}

	private String getRandomName() {
		productNames = Arrays.asList(names);
		return getRandom(productNames);
	}

	private String getRandom(List<String> elements) {
		return elements.get(random.nextInt(elements.size()));
	}

	private BigDecimal getRandomCost() {
		return new BigDecimal(COST_BASE + Math.random() * COST_SPREAD);
	}

}
