package com.instacart.enties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Entity
@Table(name = "buyers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    private String password;

    @Min(18)
    private int age;

    @Column(unique = true)
    private Long contactNo;

    private String city;

    private String userType; 

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private String status = "active"; // Default status

  

    // Check if user is blocked based on status
    public boolean isBlocked() {
        return "blocked".equalsIgnoreCase(this.status);
    }

    // Return current status
    public String getStatus() {
        return this.status;
    }
}

