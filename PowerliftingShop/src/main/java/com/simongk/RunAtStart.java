package com.simongk;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simongk.repositories.ProductRepository;
import com.simongk.service.ProductGenerator;

@Component
public class RunAtStart {

	private ProductRepository productRepository;
	private ProductGenerator productGenerator;

	@Autowired
	public RunAtStart(ProductRepository productRepository, ProductGenerator productGenerator) {
		this.productRepository = productRepository;
		this.productGenerator = productGenerator;
	}
	
	@PostConstruct
	public void runAtStart(){
		for(int i = 0; i<50;i++){
			productRepository.save(productGenerator.generate());
		}
	}
	
	
	
}
