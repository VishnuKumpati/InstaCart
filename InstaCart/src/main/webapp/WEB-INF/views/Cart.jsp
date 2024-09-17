<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.*"%>
<%@page import="com.instacart.entities.Cart"%>
<%@page import="com.instacart.entities.CartItem"%>
<%@page import="com.instacart.entities.Product"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Cart.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
 <script type="text/javascript">
        function placeOrder() {
            // Get the buyerId from the hidden input field
            var buyerId = $('input[name="id"]').val();
                 console.log("hi");
                 console.log("buyerid"+buyerId)
            $.ajax({
            	
                type: "POST",
                url: "/buyer/placeorder/"+buyerId,
                data: {
                    id: buyerId
                },
                success: function(response) {
                    // Redirect to cart page or show success message
                    alert("Order placed successfully!");
                   
                },
                error: function(xhr, status, error) {
                    // Show error message
                    alert("Failed to place order: " + xhr.responseText);
                }
            });
        }
    </script>
</head>
<body>
 <div class="cart-container">
    <h2>Your Cart</h2>

    <%
        Cart buyercart = (Cart) request.getAttribute("buyercart");
        if (buyercart != null && buyercart.getItems() != null) {
            for (CartItem item : buyercart.getItems()) {
                Product product = item.getProduct();
    %>
    <div class="cart-item">
        <!-- Product Image -->
        <img src="<%= product.getImagePath() %>" alt="<%= product.getProductName() %>">

        <!-- Product Info -->
        <div class="product-info">
            <h4><%= product.getProductName() %></h4>
            <p>Quantity: 
                <input type="number" value="<%= item.getQuantity() %>" min="1" class="quantity" data-unit-price="<%= item.getUnitPrice() %>" data-id="<%= item.getId() %>">
            </p>
            <p>Unit Price: ₹<%= item.getUnitPrice() %></p>
            <p>Total Price: ₹<%= item.getTotalPrice() %></p>
        </div>
    </div>
    <% 
            } 
        } else {
    %>
    <p>Your cart is empty.</p>
    <% } %>

    <!-- Total Amount -->
    <div class="total-amount">
        <p>Total Amount: ₹<%= buyercart != null ? buyercart.getTotalAmount() : 0 %></p>
    </div>

    <!-- Place Order Button -->
    <div class="place-order">
            <input type="hidden" name="id" value="<%= buyercart != null ? buyercart.getBuyer().getId() : 0 %>">
               <button type="button" class="place-order-btn" onclick="placeOrder()">Place Order</button>
       
    </div>
</div>

</body>
</body>
</html>