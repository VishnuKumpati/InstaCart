package com.instacart.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;
    
    
    public void addItem(CartItem item) {
        this.items.add(item);
        item.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.totalAmount = items.stream().map(item -> {
            BigDecimal unitPrice = item.getUnitPrice();
            if (unitPrice == null) {
                return  BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void clearCart(){
        this.items.clear();
        updateTotalAmount();
    }
}
