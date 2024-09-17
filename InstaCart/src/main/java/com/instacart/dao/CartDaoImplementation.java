package com.instacart.dao;

import java.math.BigDecimal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.instacart.entities.Cart;
import com.instacart.entities.Product;
import com.instacart.exception.ResourceNotFoundException;

@Repository
public class CartDaoImplementation implements CartDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Cart getCart(Long id) {
		System.out.println("indie the get Cart");
		Cart cart=null;
		Session session = sessionFactory.openSession();
		 // Fetch the cart by ID using the correct entity name
      Query<Cart> query = session.createQuery("from Cart c where c.id = :id", Cart.class);
      query.setParameter("id", id);
//      //uniqueResult()Execute the query and return the single result of the query, 
//      //or null if the query returns no results.
      
          cart = query.uniqueResult();
          System.out.println(cart);
          if(cart==null) {
        	  throw new ResourceNotFoundException("cart is Not found with"+id);
          }
          else {
        	  BigDecimal totalAmount = cart.getTotalAmount();
        	  cart.setTotalAmount(totalAmount);
        	  session.persist(cart);
          }
          System.out.println("indie get cart");
          System.out.println(cart);
    	  return cart;
	}

	@Override
	public Cart getBuyerCartById(Long id) {
		Cart cart=null;
		   Session session=sessionFactory.openSession();
		   Query<Cart> query = session.createQuery("from Cart c where c.buyer.id = :id", Cart.class);
		   query.setParameter("id",id);
		   cart=query.uniqueResult();
	       return cart;
}

		
		
		
		
		
		
		
	
}
