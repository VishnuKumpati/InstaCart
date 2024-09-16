<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 450px;
        }

        h1 {
            text-align: center;
            color: #007bff;
            margin-bottom: 20px;
            font-size: 24px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-top: 12px;
            color: #333;
            font-weight: bold;
        }

        input,
        select {
            padding: 12px;
            margin-top: 8px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            transition: border-color 0.3s ease-in-out;
        }

        input:focus,
        select:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        input[type="submit"] {
            margin-top: 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
            padding: 12px;
            font-size: 16px;
            border-radius: 5px;
            transition: background-color 0.3s ease-in-out, transform 0.2s;
            font-weight: bold;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }

        p {
            text-align: center;
            margin-top: 15px;
            font-size: 14px;
        }

        a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .hidden {
            display: none;
        }
        .error {
            color: red;
            font-size: 14px;
            margin-top: 10px;
            text-align: center;
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>Register</h1>
        <form id="registrationForm" action="/registerUser" method="post">
            <label for="userType">User Type:</label>
            <select id="userType" name="userType" required>
                <option value="" disabled selected>Select User Type</option>
                <option value="admin">Admin</option>
                <option value="retailer">Retailer</option>
                <option value="customer">Customer</option>
            </select>

            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required />
            </div>

            <!-- Customer/Buyer Fields -->
            <div id="customerFields" class="hidden">
                <div class="form-group">
                    <label for="age">Age:</label>
                    <input type="number" id="age" name="age" placeholder="Enter your age" />
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required />
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required />
            </div>

            <div class="form-group">
                <label for="contactNo">Contact Number:</label>
                <input type="tel" id="contactNo" name="contactNo" />
            </div>

            <div class="form-group">
                <label for="city">City:</label>
                <input type="text" id="city" name="city" />
            </div>

            <!-- Admin Fields -->
            <div id="adminFields" class="hidden">
                <div class="form-group">
                    <label for="adminPasskey">Admin Passkey:</label>
                    <input type="password" id="adminPasskey" name="adminPasskey" placeholder="Enter admin passkey" />
                </div>
            </div>

            <!-- Retailer Fields -->
            <div id="retailerFields" class="hidden">
                <div class="form-group">
                    <label for="aadharNumber">Aadhar Number:</label>
                    <input type="text" id="aadharNumber" name="aadharNumber" placeholder="Enter Aadhar Number" />
                </div>

                <div class="form-group">
                    <label for="panNumber">PAN Number:</label>
                    <input type="text" id="panNumber" name="panNumber" placeholder="Enter PAN Number" />
                </div>

                <div class="form-group">
                    <label for="gstNumber">GST Number:</label>
                    <input type="text" id="gstNumber" name="gstNumber" placeholder="Enter GST Number" />
                </div>
            </div>

            <input type="submit" id="registerButton" value="Register" />
            <p>Already have an account? <a href="/login">Login here</a></p>
               <div class="error" th:if="${error != null}" th:text="${error}"></div>
    </div>
            
        </form>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const userTypeSelect = document.getElementById('userType');
            const adminFields = document.getElementById('adminFields');
            const retailerFields = document.getElementById('retailerFields');
            const customerFields = document.getElementById('customerFields');
            const registerButton = document.getElementById('registerButton');

            userTypeSelect.addEventListener('change', function () {
                adminFields.classList.add('hidden');
                retailerFields.classList.add('hidden');
                customerFields.classList.add('hidden');

                if (userTypeSelect.value === 'admin') {
                    adminFields.classList.remove('hidden');
                    registerButton.value = "Register as Admin";
                } else if (userTypeSelect.value === 'retailer') {
                    retailerFields.classList.remove('hidden');
                    registerButton.value = "Request Approval as Retailer";
                } else if (userTypeSelect.value === 'customer') {
                    customerFields.classList.remove('hidden');
                    registerButton.value = "Register as Customer";
                }
            });
        });
    </script>
</body>

</html>