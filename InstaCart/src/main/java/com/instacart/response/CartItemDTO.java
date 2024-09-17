package com.instacart.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data


public class CartItemDTO {
	
	private Long id;                 // CartItem ID
	    private int quantity;            // Quantity of the product in the cart
	    private BigDecimal unitPrice;    // Price per unit of the product
	    private BigDecimal totalPrice;   // Total price for the quantity of product
	    public CartItemDTO(Long id, int quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
			this.id=id;
			this.quantity=quantity;
			this.unitPrice=unitPrice;
			this.totalPrice=totalPrice;
			
		}
}
