package com.instacart.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter

public class CartDTO {
	  
	  private Long id;                 // Cart ID
	  private BigDecimal totalAmount;  // Total amount of the cart
	  private List<CartItemDTO> items; // List of cart items
      private String buyerName;
      public CartDTO(Long id, BigDecimal totalAmount, String buyerName, List<CartItemDTO> items) {
    	    this.id = id;
    	    this.totalAmount = totalAmount;
    	    this.buyerName = buyerName;
    	    this.items = items;
    	}  
}
