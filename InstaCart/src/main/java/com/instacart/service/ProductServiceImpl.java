package com.instacart.service;

import java.math.BigDecimal;
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

	@Override
	public Product addProduct(Product product) {
		
		return productDAO.addProduct(product);
	}

	@Override
	public List<Product> getAllProducts() {	
	List<Product> allproducts=	productDAO.getAllProducts();
		return allproducts;
	}

	@Override
	public Product updateProduct(Long productId, BigDecimal productPrice, BigDecimal productDiscount,
			int countofproducts) {
		Product updatedProdut=productDAO.updateproduct(productId,productPrice,productDiscount,countofproducts);
		return updatedProdut;
	}

	@Override
	public Product getproductById(Long productId) {
	Product product=productDAO.getProductByid(productId);
		return product;
	}

	
}

