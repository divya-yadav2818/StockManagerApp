package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.maruti.dao.ProductDAO;

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idStr = request.getParameter("productId");
        if (idStr != null) {
            try {
                int productId = Integer.parseInt(idStr);
                ProductDAO dao = new ProductDAO();
                dao.deleteProduct(productId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("inventory.jsp");
    }
}
