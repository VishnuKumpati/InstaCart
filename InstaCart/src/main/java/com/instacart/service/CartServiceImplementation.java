package com.instacart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instacart.dao.CartDao;
import com.instacart.entities.Cart;

@Service
public class CartServiceImplementation implements CartService {
    @Autowired
	private CartDao cartDao;

	@Override
	public Cart getCart(Long id) {
		Cart cart=cartDao.getCart(id);
		return cart;
	}

	@Override
	public Cart getBuyerCartById(Long id) {
		Cart cart=cartDao.getBuyerCartById(id);
		
		return cart ;
	}
	
}
