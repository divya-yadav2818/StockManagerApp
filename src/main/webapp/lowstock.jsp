<%@ page import="java.util.*,com.maruti.dao.ProductDAO,com.maruti.models.Product,com.maruti.models.User" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if(loggedUser == null){ response.sendRedirect("login.jsp"); return; }
    int userId = loggedUser.getUserId();
    ProductDAO dao = new ProductDAO();
    List<Product> products = dao.getAllProducts(userId);
    
    // ===== DARK MODE FROM SESSION =====
    char darkMode = 'N';
    if(session.getAttribute("darkMode") != null){
        darkMode = (char) session.getAttribute("darkMode");
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Low Stock</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<header class="top-bar">
    <div class="left">
        <h1>MARUTI</h1>
        <h2>Low Stock Items</h2>
        <p>Welcome, <%= loggedUser.getFirstName() %></p>
    </div>

    <div class="right">
        <button onclick="location.href='home.jsp'">Home</button>
        <button onclick="location.href='inventory.jsp'">Inventory</button>
        <button onclick="location.href='settings.jsp'">Settings</button>
    </div>
</header>


<table>
<thead>
<tr>
<th>Name</th><th>Unit</th><th>Qty</th><th>Min Qty</th><th>Type</th><th>Last Updated</th>
</tr>
</thead>
<tbody>
<%
    for(Product p : products){
        if(p.getQty() <= p.getMinQty()){
%>
<tr class="low-stock">
<td><%=p.getName()%></td>
<td><%=p.getUnit()%></td>
<td><%=p.getQty()%></td>
<td><%=p.getMinQty()%></td>
<td><%=p.getType()%></td>
<td><%=p.getLastUpdated()%></td>
</tr>
<% }} %>
</tbody>
</table>
<script src="js/main.js"></script>
</body>
</html>
