package com.simongk.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.simongk.domain.Cart;
import com.simongk.repositories.CartRepository;

@Controller
@RequestMapping("/cart")
public class CartController {

	private CartRepository repository;

	@Autowired
	public CartController(CartRepository repository) {
		this.repository = repository;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listProducts(Model model) {
		model.addAttribute("carts", getCartProducts());
		return "products/cart";
	}

	@RequestMapping(value = "/{id}/delete")
	public ModelAndView delete(@PathVariable long id) {
		repository.delete(id);
		return new ModelAndView("redirect:/cart");
	}

	@RequestMapping(value = "/{id}/update")
	public ModelAndView updateQuantity(@PathVariable long id) {
		Cart cart = repository.findOne(id);
		cart.setQuantity(cart.getQuantity() + 1);
		cart.setCartCost(setActualCost(cart));
		repository.save(cart);
		return new ModelAndView("redirect:/cart");
	}

	private BigDecimal setActualCost(Cart cart) {
		return new BigDecimal(cart.getProduct().getCost().doubleValue() * cart.getQuantity());
	}

	private Iterable<Cart> getCartProducts() {
		return repository.findAll();
	}


}
