<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Products </title>
<link rel='stylesheet' href='displayAllProducts.css'>
</head>
<body>
<div class="product-container">
        <c:forEach var="product" items="${allproducts}">
            <div class="product-card">
                <img src="${product.imagePath}" alt="${product.productName}" class="product-image" />
                <div class="product-info">
                    <h4>${product.productName}</h4>
                    <p>Brand: ${product.productBrand}</p>
                    <p>Retailer: ${product.name}</p>
                    <p>Price: ${ product.productPrice}</p>
                    <p class="discount">₹${product.productDiscount}</p>
                    <p class="price">Price: ₹${product.productPriceAfterDiscount}</p>
                    
                    <p>${product.description}</p>
                </div>
                
                <!-- Form for Add to Cart -->
                <form action="addToCart" method="post">
                <input type="hidden" name="imagePath" value="${product.imagePath}" /> <!-- Product image path -->
                    <input type="hidden" name="productId" value="${product.productId}" />
                    <input type="hidden" name="productName" value="${product.productName}" />
                     <input type="hidden" name="productdescription" value="${product.description}" />
                    <input type="hidden" name="productPrice" value="${product.productPrice}" />
                    <input type="hidden" name="productDiscount" value="${product.productDiscount}" />
                    <input type="hidden" name="productPriceAfterDiscount" value="${product.productPriceAfterDiscount}" />
                    <input type="hidden" name="retailername" value="${product.name}" />
                     
                    <button type="submit" class="btn btn-cart">Add to Cart</button>
                </form>
 <!-- Add to Cart Button (Using AJAX) -->
            <button class="btn btn-cart" onclick="addToCart(${product.productId}, 1)">Add to Cart</button>
                <!-- Form for Buy Now -->
                <form action="order.jsp" method="post">
                   <input type="hidden" name="imagePath" value="${product.imagePath}" /> <!-- Product image path -->
                    <input type="hidden" name="productId" value="${product.productId}" />
                    <input type="hidden" name="productName" value="${product.productName}" />
                     <input type="hidden" name="productdescription" value="${product.description}" />
                    <input type="hidden" name="productPrice" value="${product.productPrice}" />
                    <input type="hidden" name="productDiscount" value="${product.productDiscount}" />
                    <input type="hidden" name="productPriceAfterDiscount" value="${product.productPriceAfterDiscount}" />
                    <input type="hidden" name="retailername" value="${product.name}" />
                </form>
            </div>
        </c:forEach>
    </div>
</body>
</html>