package com.instacart.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(unique = true, nullable = false)
    private String productName;

    private String productBrand;

    private BigDecimal productPrice;

    private BigDecimal productDiscount;

    private BigDecimal productPriceAfterDiscount;

    private String description;

    private int countofproducts;
    
    private String imagePath;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name = "retailer_id") // Ensure this column matches the primary key of Retailer
    private Retailer retailer;

	public Long getProductId() {
		return productId;
	}
	
	
	
	public Product() {
		super();
		
	}



	public Product(Long productId, String productName, String productBrand, BigDecimal productPrice,
			BigDecimal productDiscount, BigDecimal productPriceAfterDiscount, String description, int countofproducts,
			String imagePath, Category category, Retailer retailer) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productBrand = productBrand;
		this.productPrice = productPrice;
		this.productDiscount = productDiscount;
		this.productPriceAfterDiscount = productPriceAfterDiscount;
		this.description = description;
		this.countofproducts = countofproducts;
		this.imagePath = imagePath;
		this.category = category;
		this.retailer = retailer;
	}
	
    
	
	
	

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(BigDecimal productDiscount) {
		this.productDiscount = productDiscount;
	}

	public BigDecimal getProductPriceAfterDiscount() {
		return productPriceAfterDiscount;
	}

	public void setProductPriceAfterDiscount(BigDecimal productPriceAfterDiscount) {
		this.productPriceAfterDiscount = productPriceAfterDiscount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCountofproducts() {
		return countofproducts;
	}

	public void setCountofproducts(int countofproducts) {
		this.countofproducts = countofproducts;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

//	@Override
//	public String toString() {
//		return "Product [productId=" + productId + ", productName=" + productName + ", productBrand=" + productBrand
//				+ ", productPrice=" + productPrice + ", productDiscount=" + productDiscount
//				+ ", productPriceAfterDiscount=" + productPriceAfterDiscount + ", description=" + description
//				+ ", countofproducts=" + countofproducts + ", imagePath=" + imagePath + ", category=" + category
//				+ ", retailer=" + retailer + "]";
//	}



	




	



	






	
   //getall update delete getby id; 
    
    
}
