package com.instacart.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.instacart.entities.Category;
import com.instacart.entities.Product;
import com.instacart.entities.Retailer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponse {
	public ProductResponse(Product product) {
		// TODO Auto-generated constructor stub
	}

	private Long productId;

	private String productName;

	private String productBrand;

	private BigDecimal productPrice;

	private BigDecimal productDiscount;

	private BigDecimal productPriceAfterDiscount;

	private String description;

	private int countofproducts;

	private String imagePath;

	private Category category;

	private String name;
	private Retailer retailer;

	public ProductResponse(String productName, String productBrand, BigDecimal productDiscount, BigDecimal productPrice,
			String name, BigDecimal productPriceAfterDiscount, Long productId, String description, String imagePath) {
		this.productName = productName;
		this.productBrand = productBrand;
		this.productDiscount = productDiscount;
		this.productPrice = productPrice;
		this.name = name;
		this.productPriceAfterDiscount = productPriceAfterDiscount;
		this.productId = productId;
		this.description = description;
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "ProductResponse [productId=" + productId + ", productName=" + productName + ", productBrand="
				+ productBrand + ", productPrice=" + productPrice + ", productDiscount=" + productDiscount
				+ ", productPriceAfterDiscount=" + productPriceAfterDiscount + ", description=" + description
				+ ", countofproducts=" + countofproducts + ", imagePath=" + imagePath + ", category=" + category
				+ ", name=" + name + ", retailer=" + retailer + "]";
	}
	

}
