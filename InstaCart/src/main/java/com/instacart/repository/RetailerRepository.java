package com.instacart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instacart.entities.Retailer;

public interface RetailerRepository extends JpaRepository<Retailer, Long> {

}
