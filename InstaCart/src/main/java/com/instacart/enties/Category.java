package com.instacart.enties;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class Category {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String name;


//	    @JsonIgnore
	    @OneToMany(mappedBy = "category")
	    private List<Product> products;

	    public Category(String name) {
	        this.name = name;
	    }

}
