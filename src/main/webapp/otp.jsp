<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String email = request.getParameter("email");
    if(email == null) {
        email = (String) session.getAttribute("signupEmail");
    }
    if(email == null){
        email = (String) session.getAttribute("loginEmail");
    }

    // ===== DARK MODE FROM SESSION =====
    char darkMode = 'N';
    if(session.getAttribute("darkMode") != null){
        darkMode = (char) session.getAttribute("darkMode");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>OTP Verification - Maruti Stock Manager</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<div class="container">
    <h2>OTP Verification</h2>
    <p>OTP sent to: <strong><%= email %></strong></p>

    <form action="OTPServlet" method="post" id="otpForm">
        <input type="text" id="otpInput" name="otp" placeholder="Enter OTP" required>
        <button type="submit" class="primary-btn">Verify OTP</button>
    </form>
</div>

<script>
    const otpForm = document.getElementById("otpForm");
    const otpInput = document.getElementById("otpInput");
    otpForm.addEventListener("submit", function(e){
        if(otpInput.value.trim() === ""){
            e.preventDefault();
            alert("Enter OTP!");
        }
    });
</script>

</body>
</html>
