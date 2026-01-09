<%@ page import="java.util.*,com.maruti.dao.ProductDAO,com.maruti.models.Product,com.maruti.models.User" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if(loggedUser == null){
        response.sendRedirect("login.jsp");
        return;
    }
    int userId = loggedUser.getUserId();
    ProductDAO dao = new ProductDAO();
    List<Product> products = dao.getAllProducts(userId);

    // Edit product redirect
    String editIdParam = request.getParameter("editId");
    Product editProduct = null;
    if(editIdParam != null){
        int editId = Integer.parseInt(editIdParam);
        for(Product p : products){
            if(p.getProductId() == editId){
                editProduct = p;
                break;
            }
        }
    }

    char darkMode = 'N';
    if(session.getAttribute("darkMode") != null){
        darkMode = (char) session.getAttribute("darkMode");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Home - Maruti Stock Manager</title>
<link rel="stylesheet" href="css/style.css">
<style>
/* Autocomplete dropdown */
#searchResults{
    position: absolute;
    background: white;
    border: 1px solid #ccc;
    max-height: 150px;
    overflow-y: auto;
    width: 200px;
    z-index: 1000;
}
.search-item{
    padding: 5px;
    cursor: pointer;
}
.search-item:hover{
    background-color: #f0f0f0;
}
</style>
</head>
<body class="<%= (darkMode=='Y') ? "dark-mode" : "" %>">

<header class="top-bar">
    <div class="left">
        <h1>MARUTI</h1>
        <p>Welcome, <%= loggedUser.getFirstName() %></p>
    </div>
    <div class="right">
        <button onclick="location.href='inventory.jsp'">Inventory</button>
        <button onclick="location.href='lowstock.jsp'">Low Stock</button>
        <button onclick="location.href='settings.jsp'">Settings</button>
        <button onclick="exitApp()">Exit</button>
    </div>
</header>

<div class="search-container" style="position: relative;">
    <input type="text" id="search" placeholder="Search product">
    <div id="searchResults"></div>
</div>

<div class="update-form">
    <h2>Update Product</h2>
    <form action="ProductServlet" method="post">
        <input type="hidden" name="originalName" id="originalName" value="<%= (editProduct!=null)?editProduct.getName():"" %>">
        Product Name: <input type="text" name="name" id="name" required value="<%= (editProduct!=null)?editProduct.getName():"" %>">
        Unit: 
        <select name="unit" id="unit">
            <option value="kg" <%= (editProduct!=null && "kg".equals(editProduct.getUnit()))?"selected":"" %>>kg</option>
            <option value="bags" <%= (editProduct!=null && "bags".equals(editProduct.getUnit()))?"selected":"" %>>bags</option>
        </select>
        Quantity: <input type="number" name="qty" id="qty" required value="<%= (editProduct!=null)?editProduct.getQty():"" %>">
        Min Qty: <input type="number" name="minQty" id="minQty" required value="<%= (editProduct!=null)?editProduct.getMinQty():"" %>">
        Type: 
        <select name="type" id="type">
            <option value="Inward" <%= (editProduct!=null && "Inward".equals(editProduct.getType()))?"selected":"" %>>Inward</option>
            <option value="Outward" <%= (editProduct!=null && "Outward".equals(editProduct.getType()))?"selected":"" %>>Outward</option>
        </select>
        <button type="submit">Update</button>
    </form>
</div>

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

// ===== SEARCH AND AUTOCOMPLETE =====
const searchInput = document.getElementById("search");
const searchResults = document.getElementById("searchResults");

searchInput.addEventListener("keyup", function(){
    let value = this.value.toLowerCase();
    searchResults.innerHTML = "";
    if(value.length < 2) return;

    let matches = products.filter(p => p.name.toLowerCase().startsWith(value));
    matches.forEach(p=>{
        let div = document.createElement("div");
        div.className = "search-item";
        div.textContent = p.name + " (" + p.unit + ")";
        div.onclick = ()=>fillForm(p);
        searchResults.appendChild(div);
    });
});

function fillForm(p){
    document.getElementById("originalName").value = p.name;
    document.getElementById("name").value = p.name;
    document.getElementById("unit").value = p.unit;
    document.getElementById("qty").value = p.qty;
    document.getElementById("minQty").value = p.minQty;
    document.getElementById("type").value = p.type;
    searchResults.innerHTML = "";
}

// ===== EXIT BUTTON =====
function exitApp(){
    if(confirm("Do you want to exit the app?")){
        window.location.href = "index.jsp";
    }
}

// ===== AUTO FILL IF EDIT =====
window.onload = () => {
    <% if(editProduct != null){ %>
        const p = {
            id: <%= editProduct.getProductId() %>,
            name: "<%= editProduct.getName() %>",
            unit: "<%= editProduct.getUnit() %>",
            qty: <%= editProduct.getQty() %>,
            minQty: <%= editProduct.getMinQty() %>,
            type: "<%= editProduct.getType() %>"
        };
        fillForm(p);
    <% } %>
};
</script>

</body>
</html>
