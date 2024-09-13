package com.instacart.service;

import java.util.List;

import com.instacart.entities.Product;

public interface ProductService {
    List<Product> getProductsByCategory(String category);
}

