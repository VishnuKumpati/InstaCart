package com.instacart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.instacart.entity.Product;
import com.instacart.service.ProductServiceInterface;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceInterface productService;

    // Endpoint to search products by name
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }
}
