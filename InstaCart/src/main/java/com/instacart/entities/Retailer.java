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
}
