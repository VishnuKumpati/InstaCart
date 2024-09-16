package com.instacart.dao;

import org.springframework.stereotype.Repository;

import com.instacart.entities.Buyer;
@Repository
public interface BuyerDaoInterface {

	boolean existsByDetails(String email, Long contactNumber);

	void save(Buyer buyer);

	Buyer findByEmailAndPassword(String email, String password);

	boolean passRecoverymail(String email);

	String updatePassword(String password, Long userId);

}
