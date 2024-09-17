package com.instacart.service;

import com.instacart.entities.Cart;

public interface CartService {

	Cart getCart(Long id);

	Cart getBuyerCartById(Long id);

}
