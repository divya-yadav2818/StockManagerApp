<%@ page import="com.maruti.models.User,com.maruti.dao.SettingsDAO,com.maruti.models.Settings" %>
<%
    User u = (User) session.getAttribute("loggedUser");
    if(u == null){
        response.sendRedirect("login.jsp");
        return;
    }

    int userId = u.getUserId();
    SettingsDAO sDao = new SettingsDAO();
    Settings s = sDao.getSettingsByUserId(userId);

    char darkMode = (s != null && s.getDarkMode() != 0) ? s.getDarkMode() : 'N';
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Settings</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<header>
<h1>MARUTI Settings</h1>
<button onclick="location.href='home.jsp'">Back</button>
<button style="float:right;background:#f44336;color:white;" onclick="location.href='LogoutServlet'">Logout</button>
</header>

<div class="settings-container">

<h2>App Lock Password</h2>
<form action="SettingsServlet" method="post">
    <input type="password" name="password" placeholder="Leave blank to remove lock">

<h2>Dark Mode</h2>
<select name="darkMode">
    <option value="Y" <%= (darkMode=='Y')?"selected":"" %>>Yes</option>
    <option value="N" <%= (darkMode=='N')?"selected":"" %>>No</option>
</select>

<button type="submit">Save</button>
</form>
</div>

</body>
</html>
