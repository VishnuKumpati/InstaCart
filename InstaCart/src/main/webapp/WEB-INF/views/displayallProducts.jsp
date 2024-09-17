<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.instacart.response.ProductResponse"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Products </title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/displayAllProducts.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function() {
    const productPrice = parseFloat(document.querySelector('input[name="productPrice"]').value);
    const productDiscount = parseFloat(document.querySelector('input[name="productDiscount"]').value);
     console.log(productPrice);
     console.log( productDiscount);
    let priceAfterDiscount;
     console.log("Hi")
    if (productDiscount === 0.0) {
        priceAfterDiscount = productPrice;
    } else {
        const discountAmount = (productPrice * productDiscount) / 100;
        priceAfterDiscount = productPrice - discountAmount;
        console.log(priceAfterDiscount)
    }

    document.getElementById('priceAfterDiscount').value = priceAfterDiscount.toFixed(2);
});
function addToCart(productId, quantity) {
	console.log("hello")
    $.ajax({
        type: "POST",
        url: "/buyer/addtocart/5",  // URL of your backend endpoint
        data: {
            productId: productId,
            quantity: quantity
        },
        success: function(response) {
        	 // Redirect to cart page
        	 console.log("hello requesting is coming when click on placeorder")
        	window.location.href = "/cart/getbuyercart/" + 5;
        },
        error: function(xhr, status, error) {
            // Show error message
            alert("Failed to add product to cart: " + xhr.responseText);
        }
    });
}
</script>
</head>
<body>
<div class="product-container">
    <%
        // Assuming the list of products is passed as an attribute "productRespose"
        List<ProductResponse> productList = (List<ProductResponse>) request.getAttribute("productRespose");
        if (productList != null && !productList.isEmpty()) {
            for (ProductResponse product : productList) {
    %>
                <div class="product-card">
                    <img src="<%= product.getImagePath() %>" alt="<%= product.getProductName() %>" class="product-image" />
                    <div class="product-info">
                        <h4><%= product.getProductName() %></h4>
                        <p>Brand: <%= product.getProductBrand() %></p>
                        <p>Retailer: <%= product.getName() %></p>
                        <p id="productPrice">Price: <%= product.getProductPrice() %></p>
                        <p class="productDiscount">Discount: <%= product.getProductDiscount() %></p>
                       <input type="text" id="priceAfterDiscount" value="<%= product.getProductPriceAfterDiscount() %>" readonly />
                        <p>Description: <%= product.getDescription() %></p>
                    </div>

                    <!-- Form for Add to Cart -->
                    <!-- Form for Buy Now -->
                    <button class="btn btn-cart" onclick="addToCart(<%= product.getProductId() %>, 1)">Add to Cart</button>
                
                </div>
    <%
            }
        } else {
    %>
        <p>No products available at the moment.</p>
    <%
        }
    %>
</div>
</html>