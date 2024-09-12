package com.instacart.dao;

import java.math.BigDecimal;
import java.util.List;

import com.instacart.entities.Product;

public interface ProductDAO {
    List<Product> findByCategory(String category);

	Product addProduct(Product product);

	List<Product> getAllProducts();

	Product updateproduct(Long productId, BigDecimal productPrice, BigDecimal productDiscount, int countofproducts);

	Product getProductByid(Long productId);


}

