<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <% // ===== DARK MODE FROM SESSION =====
    char darkMode = 'N';
    if(session.getAttribute("darkMode") != null){
        darkMode = (char) session.getAttribute("darkMode");
    } %>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<div class="container card">
    <h2>Reset Password</h2>

    <form action="ForgotPasswordServlet" method="post">
        <label>Registered Mobile Number</label>
        <input type="text" name="mobile" required>
        <button class="primary">Send OTP</button>
    </form>
</div>
</body>
</html>