package com.simongk;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.simongk.repositories.ProductRepository;
import com.simongk.service.ProductGenerator;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RunAtStart {

	private ProductRepository productRepository;
	private ProductGenerator productGenerator;
	
	@PostConstruct
	public void runAtStart(){
		for(int i = 0; i<50;i++){
			productRepository.save(productGenerator.generate());
		}
	}
	
	
	
}
