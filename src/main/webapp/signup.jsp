<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% char darkMode = 'N';
if(session.getAttribute("darkMode") != null){ darkMode = (char) session.getAttribute("darkMode"); } %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign Up - Maruti Stock Manager</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<div class="container">
    <h2>Sign Up</h2>
    <form action="SignupServlet" method="post">
        <input type="text" name="firstName" placeholder="First Name" required>
        <input type="text" name="lastName" placeholder="Last Name" required>
        <input type="text" name="username" placeholder="Username" required>
        <input type="email" name="email" placeholder="Email Address" required>
        <input type="text" name="mobile" placeholder="Mobile Number" required>
        <button type="submit" class="primary-btn">Sign Up</button>
    </form>
    <p>Already have an account? <a href="login.jsp">Login</a></p>
</div>
</body>
</html>
