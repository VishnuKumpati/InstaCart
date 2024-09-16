package com.instacart.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

//    private BigDecimal price;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
    }
}
