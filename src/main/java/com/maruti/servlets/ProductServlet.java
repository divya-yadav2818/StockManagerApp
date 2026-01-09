package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.maruti.dao.ProductDAO;
import com.maruti.models.Product;
import com.maruti.models.User;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User loggedUser = (User) session.getAttribute("loggedUser");
        int userId = loggedUser.getUserId();

        String originalName = request.getParameter("originalName");
        String name = request.getParameter("name");
        String unit = request.getParameter("unit");
        String qtyStr = request.getParameter("qty");
        String minQtyStr = request.getParameter("minQty");
        String type = request.getParameter("type");

        if (name == null || unit == null || qtyStr == null || minQtyStr == null || type == null
                || name.trim().isEmpty() || unit.trim().isEmpty() || type.trim().isEmpty()) {
            response.sendRedirect("home.jsp");
            return;
        }

        int qty = Integer.parseInt(qtyStr);
        int minQty = Integer.parseInt(minQtyStr);

        ProductDAO dao = new ProductDAO();

        Product p = new Product();
        p.setUserId(userId);
        p.setName(name);
        p.setUnit(unit);
        p.setMinQty(minQty);
        p.setType(type);
        p.setLastUpdated(new java.sql.Timestamp(System.currentTimeMillis()));

        if ("Outward".equalsIgnoreCase(type)) {
            // Outward stock: decrease quantity of existing product
            Product existing = dao.getProductByNameUnit(userId, originalName, unit);
            if (existing != null) {
                int newQty = existing.getQty() - qty;
                if (newQty < 0) newQty = 0;
                p.setQty(newQty);
                p.setProductId(existing.getProductId());
                dao.updateProduct(p); // update by product_id
            } else {
                // Cannot outward non-existing product
                response.sendRedirect("home.jsp");
                return;
            }
        } else {
            // Inward stock: increase quantity of existing product or insert new
            Product existing = dao.getProductByNameUnit(userId, name, unit);
            if (existing != null) {
                int newQty = existing.getQty() + qty;
                p.setQty(newQty);
                p.setProductId(existing.getProductId());
                dao.updateProduct(p);
            } else {
                p.setQty(qty);
                dao.insertProduct(p);
            }
        }

        // Redirect to inventory page to show updated stock
        response.sendRedirect("inventory.jsp");
    }
}
