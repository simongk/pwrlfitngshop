package com.simongk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simongk.domain.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{

}
