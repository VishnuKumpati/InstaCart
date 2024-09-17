package com.instacart.dao;

import com.instacart.entities.Cart;

public interface CartDao {

	Cart getCart(Long id);

	Cart getBuyerCartById(Long id);

}
