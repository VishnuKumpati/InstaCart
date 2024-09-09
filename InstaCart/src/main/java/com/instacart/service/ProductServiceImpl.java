package com.instacart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instacart.dao.ProductDAO;
import com.instacart.entities.Product;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productDAO.findByCategory(category);
    }
}

