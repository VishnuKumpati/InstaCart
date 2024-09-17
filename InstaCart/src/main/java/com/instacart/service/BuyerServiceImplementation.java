package com.instacart.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instacart.dao.BuyerDaoService;
import com.instacart.entities.Buyer;
import com.instacart.entities.Cart;
import com.instacart.entities.Order;
import com.instacart.entities.OrderItem;
import com.instacart.entities.Product;
import com.instacart.enums.OrderStatus;
import com.instacart.exception.ResourceNotFoundException;
import com.instacart.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class BuyerServiceImplementation implements BuyerService {
@Autowired
private	BuyerDaoService buyerDaoService;
@Autowired
private CartService cartService;
@Autowired
private ProductRepository productRepository;
@Override
public Cart initializeNewCart(Buyer buyer) {
	Cart cart=buyerDaoService.initializeNewCart(buyer);
	return cart;
}
	@Override
	public Buyer getBuyerById(Long buyerId) {
		Buyer buyer=buyerDaoService.getBuyerById(buyerId);
		return buyer;
	}
	@Override
	public Cart addItemTocart(Long id, Long productId, Integer quantity) {
		Cart cart=buyerDaoService.addItemTocart(id,productId,quantity);
		return cart;
		
	}

	@Override
	public Order createOrder(Long id) {
		Cart cart=cartService.getBuyerCartById(id);
		Order order=createOrder(cart);
		List<OrderItem> orderItemList=craeteOrderItems(order,cart);
		order.setOrderItems(new HashSet<>(orderItemList));
		order.setTotalAmount(calculateTotalAmount(orderItemList));
		Order saveOrder=buyerDaoService.createOrder(order);
		return saveOrder;
	}
	private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
		// TODO Auto-generated method stub
		return orderItemList.stream().map(item->item.getPrice().multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO,BigDecimal::add);
	}
	private Order createOrder(Cart cart) {
		 Order order=new Order();
		 order.setBuyer(cart.getBuyer());
		 order.setOrderStatus(OrderStatus.PENDING);
		 order.setOrderDate(LocalDate.now());
		 
			return order;
		}
	
	private List<OrderItem> craeteOrderItems(Order order, Cart cart) {
		
		return cart.getItems().stream().map(cartItem->{
			Product product=cartItem.getProduct();
		        int stock=product.getCountofproducts();
		        	try{
		        		if(stock>=cartItem.getQuantity()) {	
		        		product.setCountofproducts(product.getCountofproducts()-cartItem.getQuantity());
		    			productRepository.save(product);
		        	}
		        	}
		        		
		        	catch(Exception e) {
		        		throw new ResourceNotFoundException("product is out of stock");
		        	}
		
			return new OrderItem(
					order,product,
					cartItem.getQuantity(),cartItem.getUnitPrice());
		}).toList();
	
	}

}
