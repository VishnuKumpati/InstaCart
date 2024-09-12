package com.instacart.service;

import java.math.BigDecimal;
import java.util.List;

import com.instacart.entities.Product;

public interface ProductService {
    List<Product> getProductsByCategory(String category);

	Product addProduct(Product product);

	List<Product> getAllProducts();

	Product updateProduct(Long productId, BigDecimal productPrice, BigDecimal productDiscount, int countofproducts);

	Product getproductById(Long productId);

	
}

