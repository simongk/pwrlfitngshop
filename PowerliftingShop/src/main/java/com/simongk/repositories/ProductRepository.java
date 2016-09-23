package com.simongk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simongk.domain.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

	public List<Product> findByName(String name);

}
