<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
     // ===== DARK MODE FROM SESSION =====
    char darkMode = 'N';
    if(session.getAttribute("darkMode") != null){
        darkMode = (char) session.getAttribute("darkMode");
    }%>
<!DOCTYPE html>
<html>
<head>
<title>Verify OTP</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">


<div class="container card">
    <h2>Verify OTP</h2>

    <form action="ResetOTPServlet" method="post">
        <input type="text" name="otp" placeholder="Enter OTP" required>
        <button class="primary">Verify</button>
    </form>
</div>

</body>
</html>
