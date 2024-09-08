package com.instacart.dao;

import com.instacart.entities.Admin;

public interface AdminDaoInterface {

	boolean existsByDetails(String email, Long contactNumber);

	void save(Admin admin);

	Admin findByEmailAndPassword(String email, String password);

	

}
