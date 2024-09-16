<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page with OTP Verification</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .login-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 1rem;
        }
        .error-message {
            color: red;
            font-size: 0.875em;
        }
        .btn-custom {
            background-color: #007bff;
            color: white;
        }
        .btn-custom:hover {
            background-color: #0056b3;
            color: white;
        }
        .hidden {
            display: none;
        }
        .mt-2 {
            margin-top: 0.5rem;
        }
        .spacer {
            margin-top: 10px;
        }
        .new-user-link {
            display: block;
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2 class="text-center mb-4">Login</h2>
        <form id="loginForm" action="/validateOtp" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>
            <div class="form-group d-flex align-items-center">
                <input type="text" id="otp" name="otp" class="form-control mr-2" placeholder="Enter OTP" disabled>
                <button type="button" id="sendOtpBtn" class="btn btn-custom">Send OTP</button>
            </div>
            <div id="otpSection" class="hidden spacer">
                <button type="button" id="verifyOtpBtn" class="btn btn-custom btn-block">Verify OTP</button>
            </div>
            <button type="submit" id="submitBtn" class="btn btn-custom btn-block mt-2" disabled>Login</button>
            <p id="message" class="text-center mt-3"></p>
        </form>
        <a href="/passwordRecovery" class="new-user-link">Forgot password?</a>
        <a href="/register" class="new-user-link">New User? Register here</a>
    </div>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const sendOtpBtn = document.getElementById("sendOtpBtn");
            const verifyOtpBtn = document.getElementById("verifyOtpBtn");
            const otpSection = document.getElementById("otpSection");
            const otpInput = document.getElementById("otp");
            const submitBtn = document.getElementById("submitBtn");
            const messageElem = document.getElementById("message");

            sendOtpBtn.addEventListener("click", async () => {
                const email = document.getElementById("email").value;
                const password = document.getElementById("password").value;

                try {
                    const response = await fetch("/sendOtp", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: new URLSearchParams({
                            email: email,
                            password: password
                        })
                    });

                    const result = await response.json();
                    if (result.success) {
                        otpSection.classList.remove("hidden");
                        otpInput.disabled = false;
                        sendOtpBtn.classList.add("btn-secondary");
                        sendOtpBtn.disabled = true;
                        messageElem.textContent = "OTP has been sent to your registered email.";
                        messageElem.classList.remove("error-message");
                    } else {
                        messageElem.textContent = result.message;
                        messageElem.classList.add("error-message");
                    }
                } catch (error) {
                    messageElem.textContent = "An error occurred. Please try again.";
                    messageElem.classList.add("error-message");
                }
            });

            verifyOtpBtn.addEventListener("click", async () => {
                const email = document.getElementById("email").value;
                const otp = document.getElementById("otp").value;

                try {
                    const response = await fetch("/verifyOtp", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: new URLSearchParams({
                            email: email,
                            otp: otp
                        })
                    });

                    const result = await response.json();
                    if (result.success) {
                        submitBtn.disabled = false;
                        messageElem.textContent = "OTP verified successfully. You can now login.";
                        messageElem.classList.remove("error-message");
                    } else {
                        messageElem.textContent = result.message;
                        messageElem.classList.add("error-message");
                    }
                } catch (error) {
                    messageElem.textContent = "An error occurred. Please try again.";
                    messageElem.classList.add("error-message");
                }
            });
        });
        
       
                 
    </script>
</body>
</html>
