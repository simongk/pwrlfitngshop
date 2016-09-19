package com.simongk.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simongk.domain.Cart;
import com.simongk.domain.Order;
import com.simongk.repositories.CartRepository;
import com.simongk.service.NotificationService;

@Controller
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	private CartRepository cartRepository;
	private NotificationService notificationService;
	private double totalCost;

	@Autowired
	public OrderController(CartRepository cartRepository, NotificationService notificationService) {
		this.cartRepository = cartRepository;
		this.notificationService = notificationService;
	}

	@RequestMapping(value = "/cart/order", method = RequestMethod.GET)
	public String orderForm(Model model) {
		model.addAttribute("order", new Order());
		return "products/order";
	}

	@RequestMapping(value = "/cart/order", method = RequestMethod.POST)
	public String orderSubmit(@ModelAttribute Order order, Model model) {
		order.setTotalCost(new BigDecimal(totalCost()));
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

	private double totalCost() {
		Iterable<Cart> cartProducts = cartRepository.findAll();
		List<BigDecimal> prices = new ArrayList<>();
		cartProducts.forEach((cart) -> prices.add(cart.getCartCost()));
		prices.forEach((price) -> totalCost = totalCost + price.toBigInteger().doubleValue());
		return totalCost;
	}

}
