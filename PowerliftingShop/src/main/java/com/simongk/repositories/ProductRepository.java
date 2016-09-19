package com.simongk.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.simongk.domain.Product;

public interface ProductRepository extends CrudRepository<Product,Long>{

	public List<Product> findByName(String name);

}
