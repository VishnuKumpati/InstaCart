package com.instacart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instacart.dao.ProductRepository;
import com.instacart.entity.Product;

@Service
public class ProductService implements ProductServiceInterface {
	@Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> searchProductsByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }
}
