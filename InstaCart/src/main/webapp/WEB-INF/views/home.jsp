<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InstaCart</title>
    <style>
       body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background: linear-gradient(rgba(255, 255, 255, 0.8), rgba(255, 255, 255, 0.8)), url('https://via.placeholder.com/1920x1080') no-repeat center center fixed;
    background-size: cover;
    color: #333;
}

header {
    background-color: rgba(0, 0, 0, 0.7); /* Slightly lighter dark overlay */
    color: #f0f0f0;
    padding: 15px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 2px 4px rgba(0,0,0,0.4);
}

header h1 {
    margin: 0;
    font-size: 2rem;
    color: #ff6f61; /* Vibrant Coral color */
}

nav {
    display: flex;
    gap: 1rem;
}

nav a {
    color: #f0f0f0;
    text-decoration: none;
    padding: 10px 15px;
    border-radius: 4px;
    transition: background 0.3s, color 0.3s;
    background: rgba(255, 255, 255, 0.3);
}

nav a:hover {
    background: rgba(255, 255, 255, 0.6);
    color: #ff6f61;
}

.container {
    padding: 20px;
}

.welcome {
    background: url('https://via.placeholder.com/1920x800') no-repeat center center;
    background-size: cover;
    color: white;
    text-align: center;
    padding: 100px 20px;
    margin-bottom: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0,0,0,0.5);
}

.welcome h2 {
    font-size: 3rem;
    margin: 0;
    color: #ffeb3b; /* Bright Yellow color */
}

.category {
    margin-bottom: 30px;
}

.category h2 {
    text-align: center;
    margin: 20px 0;
    font-size: 2.5rem;
    color: #ff6f61; /* Vibrant Coral color */
    font-weight: bold;
    text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
}

.product-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr); /* Three items per row */
    gap: 20px;
}

.product {
    background: #ffffff;
    border: 1px solid #ddd;
    box-shadow: 0 4px 8px rgba(0,0,0,0.3);
    border-radius: 10px;
    text-align: center;
    padding: 20px;
    transition: transform 0.3s;
}

.product img {
    width: 100%;
    height: 200px;
    object-fit: cover;
    border-radius: 10px;
}

.product:hover {
    transform: scale(1.05);
    box-shadow: 0 6px 12px rgba(0,0,0,0.4);
}

footer {
    background-color: #333; /* Dark Gray background */
    color: #f0f0f0;
    text-align: center;
    padding: 20px;
    box-shadow: 0 -2px 4px rgba(0,0,0,0.4);
    position: relative;
    bottom: 0;
    width: 100%;
}

footer p {
    margin: 10px 0;
}

footer .footer-links {
    margin: 10px 0;
    display: flex;
    justify-content: center;
    gap: 15px;
}

footer .footer-links a {
    color: #ffeb3b; /* Bright Yellow color */
    text-decoration: none;
}

footer .footer-links a:hover {
    text-decoration: underline;
}

    </style>
</head>
<body>

<header>
    <h1>InstaCart</h1>
    <nav>
        <a href="/login">Login</a>
        <a href="/register">Sign Up</a>
        <a href="#">Contact Us</a>
    </nav>
</header>

<div class="container">

    <!-- Welcome Page -->
    <div class="welcome">
        <h2>Welcome to InstaCart!</h2>
        <p>Your one-stop shop for all your needs.</p>
    </div>

    <!-- Electronics Section -->
    <section class="category" id="electronics">
        <h2>Electronics</h2>
        <div class="product-grid">
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Smartphone"><p>Smartphone</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Laptop"><p>Laptop</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Camera"><p>Camera</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Headphones"><p>Headphones</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Tablet"><p>Tablet</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Smartwatch"><p>Smartwatch</p></div>
        </div>
    </section>

    <!-- Clothes Section -->
    <section class="category" id="clothes">
        <h2>Clothes</h2>
        <div class="product-grid">
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Men's Wear"><p>Men's Wear</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Women's Wear"><p>Women's Wear</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Fashion Accessories"><p>Fashion Accessories</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Children's Wear"><p>Children's Wear</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Footwear"><p>Footwear</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Sportswear"><p>Sportswear</p></div>
        </div>
    </section>

    <!-- Accessories Section -->
    <section class="category" id="accessories">
        <h2>Accessories</h2>
        <div class="product-grid">
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Bags"><p>Bags</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Jewelry"><p>Jewelry</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Watches"><p>Watches</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Belts"><p>Belts</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Sunglasses"><p>Sunglasses</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Scarves"><p>Scarves</p></div>
        </div>
    </section>

    <!-- Other Products Section -->
    <section class="category" id="other-products">
        <h2>Other Products</h2>
        <div class="product-grid">
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Home Appliances"><p>Home Appliances</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Books"><p>Books</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Toys"><p>Toys</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Furniture"><p>Furniture</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Sports Equipment"><p>Sports Equipment</p></div>
            <div class="product"><img src="https://via.placeholder.com/200x180" alt="Gardening Tools"><p>Gardening Tools</p></div>
        </div>
    </section>

</div>

<footer>
    <p>&copy; 2024 InstaCart. All rights reserved.</p>
    <div class="footer-links">
        <a href="#">Privacy Policy</a>
        <a href="#">Terms of Service</a>
        <a href="#">Support</a>
        <a href="#">Contact Us</a>
    </div>
    <p>Support: support@instacart.com | Contact: contact@instacart.com</p>
</footer>

</body>
</html>
