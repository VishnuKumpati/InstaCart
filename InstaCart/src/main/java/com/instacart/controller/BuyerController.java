package com.instacart.controller;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.instacart.entities.Buyer;
import com.instacart.entities.Cart;
import com.instacart.entities.CartItem;
import com.instacart.entities.Order;
import com.instacart.entities.OrderItem;
import com.instacart.entities.Product;
import com.instacart.enums.OrderStatus;
import com.instacart.response.CartDTO;
import com.instacart.response.CartItemDTO;
import com.instacart.service.BuyerService;
import com.instacart.service.CartService;
import com.instacart.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.*;
@Controller
@RequestMapping("/buyer")
public class BuyerController {

	@Autowired
	private BuyerService buyerService;
	@Autowired
	private ProductService productService;
     @Autowired
	private CartService cartService;
	
//	@PostMapping("/addtocart")
//	public ModelAndView addItemToCart(HttpServletRequest request, @RequestParam Long productId,
//			@RequestParam(required = false, defaultValue = "1") Integer quantity) 
    @PostMapping("/addtocart/{buyerId}")
	public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long buyerId, @RequestParam Long productId,
			@RequestParam(required = false, defaultValue = "1") Integer quantity){
    
		
		System.out.println("getting request from from front end");
//		
//		HttpSession session = request.getSession();
//
//		
//		// Get the session object
//		System.out.println("getting request");
//		HttpSession session = request.getSession();
//		// Retrieve the userId from the session
//		Long buyerId = (Long) session.getAttribute("BuyerId");
		
		if (buyerId == null) {
			// Handle case where user is not logged in
			//return new ModelAndView("login").addObject("message", "Please log in to add products to the cart.");
			System.out.println("No buyerId");
		}
		// Fetch buyer details after login
		Buyer buyer=buyerService.getBuyerById(buyerId);
		System.out.println( buyer.getId());
		 // Store buyer details in session
		Cart cart=buyerService.initializeNewCart(buyer);
		System.out.println("cartid is"+cart.getId());
		Cart addItem = buyerService.addItemTocart(cart.getId(),productId, quantity);
	    System.out.println(addItem);
	     CartDTO cartResponse = new CartDTO(addItem.getId(),addItem.getTotalAmount(),addItem.getBuyer().getName(),
			addItem.getItems().stream().map
			(item->new CartItemDTO(item.getId(),
					item.getQuantity(),item.getUnitPrice(),item.getTotalPrice())).collect(Collectors.toList()));
	 return ResponseEntity.ok(cartResponse);
//	     ModelAndView mv = new ModelAndView();
//	     mv.addObject("buyercart", cartResponse);
//	     mv.setViewName("cart"); // This maps to cart.jsp
//
//	     return mv;
	}

	@GetMapping("/getbuyerbyid/{buyerid}")
	public ResponseEntity<Buyer> getBuyerById(@PathVariable Long buyerid){
		Buyer buyer=buyerService.getBuyerById(buyerid);
		return ResponseEntity.ok(buyer);
	}
	
	@PostMapping("/placeorder/{id}")
	public ResponseEntity<Order> createOrder(@PathVariable Long id){
		System.out.println("getting request from frontend");
	Order order=buyerService.createOrder(id);
	if(order!=null) {
		System.out.println("orderplaced sucessfully");
	}
		return ResponseEntity.ok(order);
	}
}

