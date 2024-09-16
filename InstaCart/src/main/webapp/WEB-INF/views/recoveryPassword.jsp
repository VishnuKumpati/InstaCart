<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Password Recovery</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .recovery-container {
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
        .mt-2 {
            margin-top: 0.5rem;
        }
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
    <div class="recovery-container">
        <h2 class="text-center mb-4">Password Recovery</h2>
        <form id="recoveryForm" action="newPassword" method="get">
            <div class="form-group">
                <label for="email">Enter Your Email:</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="otp">Enter OTP:</label>
                <input type="text" id="otp" name="otp" class="form-control" placeholder="Enter OTP" disabled>
            </div>
            <div class="form-group">
                <button type="button" id="sendOtpBtn" class="btn btn-custom">Send OTP</button>
                <button type="button" id="verifyOtpBtn" class="btn btn-custom hidden">Verify OTP</button>
            </div>
            <button type="submit" id="recoverBtn" class="btn btn-custom btn-block mt-2" disabled>Recover Password</button>
            <p id="message" class="text-center mt-3"></p>
        </form>
    </div>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const sendOtpBtn = document.getElementById("sendOtpBtn");
            const verifyOtpBtn = document.getElementById("verifyOtpBtn");
            const otpInput = document.getElementById("otp");
            const recoverBtn = document.getElementById("recoverBtn");
            const messageElem = document.getElementById("message");

            sendOtpBtn.addEventListener("click", async () => {
                const email = document.getElementById("email").value;

                try {
                    const response = await fetch("passwordRecovery", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: new URLSearchParams({ email: email })
                    });

                    const result = await response.json();
                    if (result.success) {
                        otpInput.disabled = false;
                        verifyOtpBtn.classList.remove("hidden");
                        messageElem.textContent = "OTP has been sent to your email.";
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
                const otp = otpInput.value;

                try {
                    const response = await fetch("verifyOtp", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: new URLSearchParams({ email: email, otp: otp })
                    });

                    const result = await response.json();
                    if (result.success) {
                        recoverBtn.disabled = false;
                        messageElem.textContent = "OTP verified successfully. You can now recover your password.";
                        messageElem.classList.remove("error-message");
                    } else {
                        messageElem.textContent = "Invalid OTP. Please try again.";
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
