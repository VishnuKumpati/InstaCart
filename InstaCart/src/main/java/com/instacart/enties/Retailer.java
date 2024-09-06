package com.instacart.enties;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "retailers")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "retailer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> product;
}

