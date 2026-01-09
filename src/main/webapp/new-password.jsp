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
    <title>Set New Password</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<div class="container card">
    <h2>Set New Password</h2>

    <form action="NewPasswordServlet" method="post">
        <input type="password" name="password" placeholder="New Password" required>
        <button class="primary">Save Password</button>
    </form>
</div>
</body>
</html>
