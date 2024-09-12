package com.instacart.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.instacart.entities.Category;
import com.instacart.entities.Product;
import com.instacart.exception.ResourceNotFoundException;
import com.instacart.repository.CategoryRepository;
import java.util.Optional;
import jakarta.persistence.EntityTransaction;


@Repository
public class ProductDAOImpl implements ProductDAO {
	@Autowired
	private CategoryRepository categoryRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Product> findByCategory(String categoryName) {
        Session session = sessionFactory.openSession();
        Query<Product> query = session.createQuery(
            "SELECT p FROM Product p WHERE p.category.name = :categoryName", Product.class);
        query.setParameter("categoryName", categoryName);
        List<Product> products = query.getResultList();
        session.close(); // Don't forget to close the session
        return products;
    }

	@Override
	public Product addProduct(Product product) {
		try {
			Session ss=sessionFactory.openSession();
//			EntityTransaction is used with JPA when you manage transactions via the EntityManager.
//			Transaction is used with Hibernate when you manage transactions via the Session object
			  EntityTransaction et=ss.getTransaction();
				et.begin();
				ss.merge(product);
				et.commit();
				ss.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
			return product;
		
	}

	@Override
	public List<Product> getAllProducts() {
		
			  Session session = sessionFactory.openSession();
				Query<Product> q = session.createQuery("from com.instacart.entities.Product p", Product.class);
				List<Product> ll = q.getResultList();
				session.close();	
		return ll;
		
	}

	@Override
	public Product updateproduct(Long productId, BigDecimal productPrice, BigDecimal productDiscount,
			int countofproducts) {
	 Product product = getProductByid(productId);
	 if(product==null) {
		 throw new ResourceNotFoundException("Product with ID " + productId + " not found.");
	 }
	 Session session = sessionFactory.openSession();
	
	        Transaction transaction = session.beginTransaction();
        // Set the updated fields
	        if(productPrice!=null) {
	        	product.setProductPrice(productPrice);
	        }
	      if(productDiscount!=null) {
	    	  product.setProductDiscount(productDiscount);
	      }
//	      Integer stockQuantity=countofproducts;
	    // a small thing actually not error i.e countofproducts is declared as primitive
	     
	      product.setCountofproducts(countofproducts);
      
//	        // Save the updated product
	        session.merge(product);
	        transaction.commit(); // Commit the transaction
 
            session.close();
		return product;
	}

	@Override
	public Product getProductByid(Long productId) {
		Product product=null;
		try {
			Session session = sessionFactory.openSession();
	        Query<Product> query = session.createQuery("from Product p where p.id = :productId", Product.class);
	        query.setParameter("productId", productId);
	        //uniqueResult()Execute the query and return the single result of the query, 
	        //or null if the query returns no results.
	        product = query.uniqueResult();
	        
	    } catch (Exception e) {
	        // Log the error and throw a custom exception if needed
	        System.err.println("Error fetching product by ID: " + e.getMessage());
	        throw new RuntimeException("Unable to fetch product", e);
	        
	    }
		return product;
	}

	
}
