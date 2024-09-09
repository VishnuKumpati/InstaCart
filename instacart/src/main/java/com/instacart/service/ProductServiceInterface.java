package com.instacart.service;

import java.util.List;

import com.instacart.entity.Product;


public interface ProductServiceInterface {
	List<Product> searchProductsByName(String productName);
}
