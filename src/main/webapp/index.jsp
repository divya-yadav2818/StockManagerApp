<%@ page import="com.maruti.models.User" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if(loggedUser == null){
        response.sendRedirect("login.jsp");
        return;
    }

    String appPassword = loggedUser.getPassword(); // stored password

    // ===== DARK MODE FROM SESSION =====
    char darkMode = 'N';
    if(session.getAttribute("darkMode") != null){
        darkMode = (char) session.getAttribute("darkMode");
    }
%>

<!DOCTYPE html>
<html>
<head>
<title>Index - MARUTI</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<div class="container card">
    <h2>Welcome, <%= loggedUser.getFirstName() %></h2>

    <% if(appPassword == null || appPassword.trim().isEmpty()) { %>
        <p><b>Password not set</b></p>
        <p>You can continue without password.</p>
        <a href="settings.jsp">Set your password</a><br><br>
        <a href="home.jsp"><button class="primary">Open Home</button></a>

    <% } else { %>
        <label>Enter App Password</label>
        <input type="password" id="appPassword">
        <button class="primary" onclick="checkPassword()">Open Home</button>
    <% } %>

    <a href="forgot-password.jsp">Forgot Password?</a>
</div>

<script>
// ===== APP LOCK CHECK =====
if(localStorage.getItem("exitApp") === "Y"){
    // user temporarily exited, show password page
    localStorage.removeItem("exitApp");
}

// ===== CHECK PASSWORD =====
function checkPassword(){
    const entered = document.getElementById("appPassword").value;
    const actual = "<%= appPassword %>";

    if(entered === actual){
        window.location.href = "home.jsp";
    } else {
        alert("Incorrect password");
    }
}

// ===== EXIT APP =====
function exitApp(){
    if(confirm("Do you want to exit the app?")){
        localStorage.setItem("exitApp", "Y");
        window.location.href = "index.jsp";
    }
}
</script>

</body>
</html>
