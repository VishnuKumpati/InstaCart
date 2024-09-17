package com.instacart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instacart.entities.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>{

}
