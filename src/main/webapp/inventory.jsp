<%@ page import="java.util.*,com.maruti.dao.ProductDAO,com.maruti.models.Product,com.maruti.models.User,com.maruti.models.Utils" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if(loggedUser == null){
        response.sendRedirect("login.jsp");
        return;
    }
    int userId = loggedUser.getUserId();
    ProductDAO dao = new ProductDAO();
    List<Product> products = dao.getAllProducts(userId);

    char darkMode = 'N';
    if(session.getAttribute("darkMode") != null){
        darkMode = (char) session.getAttribute("darkMode");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Inventory - Maruti Stock Manager</title>
<link rel="stylesheet" href="css/style.css">
<style>
.low-stock { background-color: #ffcccc; } 
#searchResults { position:absolute; background:#fff; z-index:100; max-height:200px; overflow:auto; width:200px; }
</style>
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<header class="top-bar">
    <div class="left">
        <h1>MARUTI</h1>
        <p>Welcome, <%= loggedUser.getFirstName() %></p>
    </div>
    <div class="right">
        <button onclick="location.href='home.jsp'">Home</button>
        <button onclick="location.href='lowstock.jsp'">Low Stock</button>
        <button onclick="location.href='settings.jsp'">Settings</button>
        <button onclick="exitApp()">Exit</button>
    </div>
</header>

<div class="search-container">
    <input type="text" id="search" placeholder="Search product">
    <div id="searchResults"></div>
</div>

<table id="productTable" border="1" cellpadding="5">
    <thead>
        <tr>
            <th>Name</th>
            <th>Unit</th>
            <th>Qty</th>
            <th>Min Qty</th>
            <th>Type</th>
            <th>Last Updated</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <%
        for(Product p : products){
            boolean lowStock = p.getQty() <= p.getMinQty();
    %>
        <tr class="<%= lowStock ? "low-stock" : "" %>">
            <td><%= p.getName() %></td>
            <td><%= p.getUnit() %></td>
            <td><%= p.getQty() %></td>
            <td><%= p.getMinQty() %></td>
            <td><%= p.getType() %></td>
            <td>
                <%= (p.getLastUpdated() != null) ? Utils.formatTimestamp(p.getLastUpdated()) : "" %>
            </td>
            <td>
                <button onclick="editProduct(<%=p.getProductId()%>)">Edit</button>
                <button onclick="deleteProduct(<%=p.getProductId()%>)">Delete</button>
            </td>
        </tr>
    <% } %>
    </tbody>
</table>

<script>
// ===== PRODUCTS DATA =====
let products = [
<%
for(int i=0;i<products.size();i++){
    Product p = products.get(i);
%>
{
    id: <%=p.getProductId()%>,
    name: "<%=p.getName()%>",
    unit: "<%=p.getUnit()%>",
    qty: <%=p.getQty()%>,
    minQty: <%=p.getMinQty()%>,
    type: "<%=p.getType()%>"
}<%= (i < products.size()-1) ? "," : "" %>
<% } %>
];

const searchInput = document.getElementById("search");
const searchResults = document.getElementById("searchResults");

searchInput.addEventListener("keyup", function(){
    let value = this.value.toLowerCase();
    searchResults.innerHTML = "";
    if(value.length < 2) return;

    let matches = products.filter(p => p.name.toLowerCase().startsWith(value));
    matches.forEach(p => {
        let div = document.createElement("div");
        div.style.cursor = "pointer";
        div.style.padding = "5px";
        div.innerHTML = p.name + " (" + p.unit + ")";
        div.onclick = () => { window.location.href = "home.jsp?editId=" + p.id; };
        searchResults.appendChild(div);
    });
});

function deleteProduct(productId){
    if(confirm("Do you want to delete this product permanently?")){
        window.location.href = "DeleteProductServlet?id=" + productId;
    }
}

function editProduct(productId){
    window.location.href = "home.jsp?editId=" + productId;
}

function exitApp(){
    if(confirm("Do you want to exit the app?")){
        localStorage.setItem("exitApp","Y");
        window.location.href = "index.jsp";
    }
}

function highlightLowStock(){
    const rows = document.getElementById("productTable").getElementsByTagName("tr");
    for(let i=1;i<rows.length;i++){
        const qty = parseInt(rows[i].getElementsByTagName("td")[2].textContent);
        const minQty = parseInt(rows[i].getElementsByTagName("td")[3].textContent);
        if(qty <= minQty){
            rows[i].classList.add("low-stock");
        }
    }
}

window.onload = () => { highlightLowStock(); };
</script>

</body>
</html>
