<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Password</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
            max-width: 90%;
        }
        h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .form-group input:focus {
            border-color: #007bff;
            outline: none;
        }
        .error {
            color: red;
            font-size: 12px;
            display: none;
        }
        .btn {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function validateForm() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            var error = document.getElementById("error");

            if (password === "" || confirmPassword === "") {
                error.textContent = "Both fields are required.";
                error.style.display = "block";
                return false;
            } else if (password !== confirmPassword) {
                error.textContent = "Passwords do not match.";
                error.style.display = "block";
                return false;
            } else if (password.length < 8) {
                error.textContent = "Password must be at least 8 characters long.";
                error.style.display = "block";
                return false;
            }

            error.style.display = "none";
            return true;
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Update Password</h2>
        <form action="updatePassword" method="post" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="password">New Password</label>
                <input type="password" id="password" name="password">
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword">
            </div>
            <div class="error" id="error"></div>
            <button type="submit" class="btn">Update Password</button>
        </form>
    </div>
</body>
</html>
