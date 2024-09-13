package com.instacart.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "retailers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"password", "email", "contactNumber"})
public class Retailer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public boolean isActive() {
		return isActive;
	}
	private Long contactNumber;

    private String city;

    private String password;
    
    @Column(name = "is_active")
    private boolean isActive = true;
    
    // Method to check status
    public String getStatus() {
        return isActive ? "active" : "blocked";
    }

    // Check if user is blocked based on status
    public boolean isBlocked() {
        return !isActive;
    }
     @JsonIgnore
    @OneToMany(mappedBy = "retailer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

     public void setActive(boolean isActive) {
    	    this.isActive = isActive;
    }

}
