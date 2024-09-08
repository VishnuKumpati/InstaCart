<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 800px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
        .error-code { font-size: 2em; color: #d9534f; }
        .error-message { font-size: 1.5em; color: #5bc0de; }
        .error-description { font-size: 1em; color: #333; }
        .home-link { margin-top: 20px; display: inline-block; padding: 10px 15px; background: #5bc0de; color: #fff; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-code">${statusCode}</div>
        <div class="error-message">${exception.message}</div>
        <div class="error-description">An error occurred while processing your request. Please try again later.</div>
        <a href="/" class="home-link">Go to Home Page</a>
    </div>
</body>
</html>
