<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
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
<title>Login - Maruti Stock Manager</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<div class="container">
    <h2>Login</h2>
    <form action="LoginServlet" method="post">
        <!-- ✅ input name is email -->
        <input type="email" name="email" placeholder="Enter registered email" required>
        <button type="submit" class="primary-btn">Send OTP</button>
    </form>
    <p>Don't have an account? <a href="signup.jsp">Sign Up</a></p>
</div>
</body>
</html>
