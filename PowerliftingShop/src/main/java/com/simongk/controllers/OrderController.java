package com.simongk.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.simongk.domain.Cart;
import com.simongk.domain.Order;
import com.simongk.repositories.CartRepository;
import com.simongk.service.NotificationService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class OrderController {

	private CartRepository cartRepository;
	private NotificationService notificationService;
	
	private BigDecimal totalCost;
	
	@Getter 
	private List<BigDecimal> prices;

	@Autowired
	public OrderController(CartRepository cartRepository, NotificationService notificationService) {
		this.cartRepository = cartRepository;
		this.notificationService = notificationService;
	}

	@GetMapping("/cart/order")
	public String orderForm(Model model) {
		model.addAttribute("order", new Order());
		return "products/order";
	}

	@PostMapping("/cart/order")
	public String orderSubmit(@ModelAttribute Order order, Model model) {
		order.setTotalCost(totalCost());
		sendOrderEmail(order);
		cartRepository.deleteAll();
		model.addAttribute("order", order);
		return "products/checkout";
	}

	private void sendOrderEmail(Order order) {
		try {
			notificationService.sendNotification(order);
		} catch (MailException e) {
			log.info("Error: " + e.getMessage());
		}
	}

	private BigDecimal totalCost() {
		List<Cart> cartProducts = cartRepository.findAll();
		prices = new ArrayList<>();
		
		totalCost = cartProducts
		.stream()
		.map(cart -> cart.getCartCost())
		.reduce(BigDecimal.ZERO,(prices,cart) ->
		prices.add(cart));

		return totalCost;
	}

}
