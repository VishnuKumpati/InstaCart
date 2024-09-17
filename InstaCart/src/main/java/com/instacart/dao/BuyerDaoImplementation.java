package com.instacart.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.instacart.entities.Buyer;
import com.instacart.entities.Cart;
import com.instacart.entities.CartItem;
import com.instacart.entities.Order;
import com.instacart.entities.Product;
import com.instacart.service.CartService;
import com.instacart.service.ProductService;


import jakarta.transaction.Transactional;

@Repository
public class BuyerDaoImplementation implements BuyerDaoService {
	@Autowired
	private ProductService productService;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private CartService cartService;

	@Override 
	public Cart initializeNewCart(Buyer buyer) {
		System.out.println("inside intializerNewCart");
		System.out.println(buyer);
		Cart cart=null;
		 Cart newCart=null;
		 Session session = sessionFactory.openSession();
		    Transaction tr = session.beginTransaction(); 
		try {
//		  Transaction tr=session.beginTransaction();
			System.out.println(buyer.getId());
		  Query<Cart> query = session.createQuery("from Cart c where c.buyer.id = :buyer_Id", Cart.class);
	        query.setParameter("buyer_Id", buyer.getId());
	        //uniqueResult()Execute the query and return the single result of the query, 
	        //or null if the query returns no results.
	        System.out.println("before fetchiing cart ");
	        cart = query.uniqueResult();
	        System.out.println("after fetching the cart" +cart);
	        if(cart!=null) {
	        	System.out.println("inside cart !=null");
	        	System.out.println(cart);
	        	return cart;
	        	
	        }else {
	        	System.out.println("inside else block");
	        	  newCart = new Cart();
	        	 newCart.setBuyer(buyer);
	            
//	             session.persi(newCart); // Save the new cart
	             session.persist(newCart);
	             tr.commit();
	             System.out.println("Before returning");
	 	        System.out.println(newCart);
	        }
	  
	       
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return newCart;
	}

	@Override
	public Buyer getBuyerById(Long buyerId) {
		Buyer buyer=null;
		try {
			Session session = sessionFactory.openSession();
	        Query<Buyer> query = session.createQuery("from Buyer b where b.id = :id", Buyer.class);
	        query.setParameter("id", buyerId);
	        //uniqueResult()Execute the query and return the single result of the query, 
	        //or null if the query returns no results.
	        buyer= query.uniqueResult();
	        
	    } catch (Exception e) {
	        // Log the error and throw a custom exception if needed
	        System.err.println("Error fetching Buyer by ID: " + e.getMessage());
	        throw new RuntimeException("Unable to BuyerDeatils", e);
	        
	    }
		return buyer;
		
	}

	//This method is responsible for adding a product to a cart
	@Override
	public Cart addItemTocart(Long id, Long productId, Integer quantity) {
		System.out.println( "the id before null"+id);
		Cart cart=null;
		System.out.println("the id after null"+id);
		System.out.println("inside the addItemtocart");
		Session session = sessionFactory.openSession();
		Transaction tr=session.beginTransaction();
		try {
		//The method first fetches the cart with the provided cartId using the cartService.getCart(id) method
		//This could fetch the cart from the database.
		  cart = cartService.getCart(id);
		  System.out.println(cart);
		//fetching the product from the producttable by id
		  Product product = productService.getproductById(productId);
		  System.out.println(product);
		  System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		  System.out.println(productId);
		  System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		  //this method check is if the product is already present in the cart. This is done by filtering through the cart.getItems() using a Stream.
		//  It looks for an item where the product's ID matches the productId. If found, it returns that CartItem.
		 // If no such item is found, it creates a new CartItem object using new CartItem().
		  CartItem cartItem = cart.getItems()
	                .stream()
	                .filter(item -> item.getProduct().getProductId().equals(productId))
	                .findFirst().orElse(new CartItem());
		  
		  //If the cartItem.getId() is null, it means that this is a new item that doesn't already exist in the cart.
		  if(cartItem.getId()==null) {
			  cartItem.setCart(cart);
			    cartItem.setProduct(product);
			    cartItem.setQuantity(quantity);
			    cartItem.setUnitPrice(product.getProductPrice());
		  }
		  else {
	            cartItem.setQuantity(cartItem.getQuantity() + quantity);
	        }
	        cartItem.setTotalPrice();
	        cart.addItem(cartItem);
	        session.merge(cart);
	        tr.commit();
	     
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
	session.close();
	}
		  return cart;	 

}

	@Override
	public Order createOrder(Order order) {
		Order placeorder=null;
		
       Session session=sessionFactory.openSession();
       Transaction tr=session.beginTransaction();
       try {
    	
    	  session.persist(order);
    	  tr.commit();
    	  placeorder=order;
       }catch(Exception e) {
    	   if(tr!=null) {
    		   tr.rollback();
    	   }
    	   e.printStackTrace();
       }finally {
    	   session.close();
       }
    return placeorder;
	}
}
