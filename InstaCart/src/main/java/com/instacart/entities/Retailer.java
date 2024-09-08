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

    private Long aadharNumber;

    private Long panNumber;

    private Long gstNumber;

    private String city;

    private String password;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "status", nullable = false)
    private String status = "Under Review"; // Initial status set to "Under Review"

    // Method to check status
    public String getStatus() {
        return this.status;
    }

    // Set status to active after admin approval
    public void approve() {
        this.status = "Active";
        this.isActive = true;
    }

    // Block retailer
    public void block() {
        this.status = "Blocked";
        this.isActive = false;
    }

    // Check if retailer is blocked
    public boolean isBlocked() {
        return "Blocked".equalsIgnoreCase(this.status);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "retailer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
