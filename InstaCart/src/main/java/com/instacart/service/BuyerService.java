package com.instacart.service;

import com.instacart.entities.Buyer;
import com.instacart.entities.Cart;
import com.instacart.entities.Order;

public interface BuyerService {



	Cart initializeNewCart(Buyer buyer);

	Buyer getBuyerById(Long buyerId);

	//id=cart_Id
	Cart addItemTocart(Long id, Long productId, Integer quantity);

	Order createOrder(Long id);

}
