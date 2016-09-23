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

	private static final String[] NAMES = { "Knee Sleeves", "Belt", "Accessory" };
	private static final String[] DESCRIPTIONS = { "Solid piece of equipment", "Worth it's price", "Get it to boost your progress" };
	private static final String[] COMPANIES = { "Titan", "SBD", "Metal", "Pionier", "Inzer", "Power System" };

	@Getter
	private List<String> productNames;
	@Getter
	private List<String> productDescriptions;
	@Getter
	private List<String> productCompanies;
	private static final BigDecimal COST_BASE = new BigDecimal(300.0);
	private static final BigDecimal COST_SPREAD = new BigDecimal(500.0);
	private final Random random = new Random();

	public Product generate() {
		return Product.builder().name(getRandomName()).description(getRandomDescription()).company(getRandomCompany())
				.cost(getRandomCost()).build();
	}

	private String getRandomCompany() {
		productCompanies = Arrays.asList(COMPANIES);
		return getRandom(getProductCompanies());
	}

	private String getRandomDescription() {
		productDescriptions = Arrays.asList(DESCRIPTIONS);
		return getRandom(getProductDescriptions());
	}

	private String getRandomName() {
		productNames = Arrays.asList(NAMES);
		return getRandom(productNames);
	}

	private String getRandom(List<String> elements) {
		return elements.get(random.nextInt(elements.size()));
	}

	private BigDecimal getRandomCost() {
		BigDecimal random = new BigDecimal(Math.random());
		BigDecimal result = COST_BASE.add(random.multiply(COST_SPREAD));
		return result;
	}

}
