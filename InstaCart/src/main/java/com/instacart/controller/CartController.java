package com.instacart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.instacart.entities.Cart;
import com.instacart.response.CartDTO;
import com.instacart.response.CartItemDTO;
import com.instacart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
@Autowired	
private CartService cartService;

//@GetMapping("/getbuyercart/{id}")
//public ResponseEntity<Cart> getBuyerCartById(@PathVariable Long id){
//	Cart buyercart=cartService.getBuyerCartById(id);
//	
//	 return ResponseEntity.ok(buyercart);
//	
//}

@GetMapping("/getbuyercart/{id}")
public ModelAndView getBuyerCartById(@PathVariable Long id){
	System.out.println("request is coming");
	Cart buyerCart=cartService.getBuyerCartById(id);
   ModelAndView mv=new ModelAndView();
	mv.addObject("buyercart", buyerCart);
	mv.setViewName("Cart");
	return mv;	 
}


}
