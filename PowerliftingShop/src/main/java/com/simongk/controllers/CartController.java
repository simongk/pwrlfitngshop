package com.simongk.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@GetMapping
	public String listProducts(Model model) {
		model.addAttribute("carts", getCartProducts());
		return "products/cart";
	}

	@GetMapping("/{id}/delete")
	public ModelAndView delete(@PathVariable long id) {
		deleteItem(id);
		return new ModelAndView("redirect:/cart");
	}

	@GetMapping("/{id}/update")
	public ModelAndView updateQuantity(@PathVariable long id) {
		updateItemQuantity(id);
		return new ModelAndView("redirect:/cart");
	}

	private void deleteItem(long id) {
		repository.delete(id);
	}

	private void updateItemQuantity(long id) {
		Cart cart = repository.findOne(id);
		cart.setQuantity(cart.getQuantity() + 1);
		cart.setCartCost(setActualCost(cart));
		repository.save(cart);
	}

	private BigDecimal setActualCost(Cart cart) {
		BigDecimal quantity = new BigDecimal(cart.getQuantity());
		BigDecimal actualCost = cart.getProduct().getCost().multiply(quantity);
		return actualCost;
	}

	private List<Cart> getCartProducts() {
		return repository.findAll();
	}

}
