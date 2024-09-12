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
                    <form action="addToCart" method="post">
                        <input type="hidden" name="imagePath" value="<%= product.getImagePath() %>" />
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>" />
                        <input type="hidden" name="productName" value="<%= product.getProductName() %>" />
                        <input type="hidden" name="productDescription" value="<%= product.getDescription() %>" />
                        <input type="hidden" name="productPrice" value="<%= product.getProductPrice() %>" />
                        <input type="hidden" name="productDiscount" value="<%= product.getProductDiscount() %>" />
                        <input type="hidden" name="productPriceAfterDiscount" value="<%= product.getProductPriceAfterDiscount() %>" />
                        <input type="hidden" name="retailerName" value="<%= product.getName() %>" />
                        <button type="submit" class="btn btn-cart">Add to Cart</button>
                    </form>

                    <!-- Form for Buy Now -->
                    <form action="order.jsp" method="post">
                        <input type="hidden" name="imagePath" value="<%= product.getImagePath() %>" />
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>" />
                        <input type="hidden" name="productName" value="<%= product.getProductName() %>" />
                        <input type="hidden" name="productDescription" value="<%= product.getDescription() %>" />
                        <input type="hidden" name="productPrice" value="<%= product.getProductPrice() %>" />
                        <input type="hidden" name="productDiscount" value="<%= product.getProductDiscount() %>" />
                        <input type="hidden" name="productPriceAfterDiscount" value="<%= product.getProductPriceAfterDiscount() %>" />
                        <input type="hidden" name="retailerName" value="<%= product.getName() %>" />
                        <button type="submit" class="btn btn-cart">Buy</button>
                    </form>
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