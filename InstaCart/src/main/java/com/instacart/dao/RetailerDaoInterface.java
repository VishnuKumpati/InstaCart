package com.instacart.dao;

import org.springframework.stereotype.Repository;

import com.instacart.entities.Retailer;
 @Repository
public interface RetailerDaoInterface {

	boolean existsByDetails(String email, Long contactNumber);

	void save(Retailer retailer);

	Retailer findByEmailAndPassword(String email, String password);

}
