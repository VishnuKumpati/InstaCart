package com.instacart.dao;

import java.util.List;

import com.instacart.entities.Product;

public interface ProductDAO {
    List<Product> findByCategory(String category);
}

