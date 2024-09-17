package com.instacart.dao;

import com.instacart.entities.Buyer;
import com.instacart.entities.Cart;
import com.instacart.entities.Order;

public interface BuyerDaoService {



	Cart initializeNewCart(Buyer buyer);

	Buyer getBuyerById(Long buyerId);

	Cart addItemTocart(Long id, Long productId, Integer quantity);

	Order createOrder(Order order);

}
