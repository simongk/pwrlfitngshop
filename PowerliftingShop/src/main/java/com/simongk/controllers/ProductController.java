package com.simongk.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.simongk.domain.Cart;
import com.simongk.domain.Product;
import com.simongk.exceptions.DuplicatedItemException;
import com.simongk.repositories.CartRepository;
import com.simongk.repositories.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductController {

	private ProductRepository repository;
	private CartRepository cartRepository;

	@Autowired
	public ProductController(ProductRepository repository, CartRepository cartRepository) {
		this.repository = repository;
		this.cartRepository = cartRepository;
	}

	@GetMapping("/{name}")
	public String getSpecificProducts(Model model, @PathVariable String name) {
		model.addAttribute("products", getByName(name));
		return "products/list";
	}


	@GetMapping("/{id}/add")
	public ModelAndView add(@PathVariable long id, HttpServletRequest request) {
		addItemToCart(id);
		return new ModelAndView("redirect:" + request.getHeader("Referer"));
	}

	private void addItemToCart(long id) throws DuplicatedItemException {
		Cart cart = new Cart();
		cart.setProduct(repository.findOne(id));
		cart.setCartCost(cart.getProduct().getCost());
		cart.setQuantity(1);
		try {
			cartRepository.save(cart);
		} catch (Exception e) {
			throw new DuplicatedItemException(
					"You already have this item in your cart");
		}
	}
	
	@ExceptionHandler(DuplicatedItemException.class)
	public ModelAndView handleDuplicateException(DuplicatedItemException ex){
		ModelAndView model = new ModelAndView("errors/duplicated");
		model.addObject("msg",ex.getMsg());
		return model;
		
	}

	private List<Product> getByName(String name) {
		List<Product> products = null;
		if (isKneeSleeves(name))
			products = getKneeSleeves();
		else if (isBelt(name))
			products = getBelt();
		else if (isAccessory(name))
			products = getAccessory();
		return products;
	}

	private List<Product> getAccessory() {
		return repository.findByName("Accessory");
	}

	private List<Product> getBelt() {
		return repository.findByName("Belt");
	}

	private List<Product> getKneeSleeves() {
		return repository.findByName("Knee Sleeves");
	}

	private boolean isAccessory(String name) {
		return name.equals("accessories");
	}

	private boolean isBelt(String name) {
		return name.equals("belts");
	}

	private boolean isKneeSleeves(String name) {
		return name.equals("kneesleeves");
	}

}
