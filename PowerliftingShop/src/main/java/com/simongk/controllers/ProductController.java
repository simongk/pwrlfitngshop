package com.simongk.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.simongk.domain.Cart;
import com.simongk.domain.Product;
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

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getSpecificProducts(Model model, @PathVariable String name) {
		model.addAttribute("products", getByName(name));
		return "products/list";
	}


	@RequestMapping(value = "/{id}/add")
	public ModelAndView add(@PathVariable long id, HttpServletRequest request) {
		Cart cart = new Cart();
		cart.setProduct(repository.findOne(id));
		cart.setCartCost(cart.getProduct().getCost());
		cart.setQuantity(1);
		try {
			cartRepository.save(cart);
		} catch (Exception e) {
			return new ModelAndView("redirect:/products/duplicateerror");
		}
		return new ModelAndView("redirect:" + request.getHeader("Referer"));
	}

	@RequestMapping(value = "/duplicateerror")
	public String duplicateError() {
		return "duplicatedAddError";
	}

	private Iterable<Product> getByName(String name) {
		Iterable<Product> list = null;
		if (isKneeSleeves(name))
			list = getKneeSleeves();
		else if (isBelt(name))
			list = getBelt();
		else if (isAccessory(name))
			list = getAccessory();
		return list;
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
