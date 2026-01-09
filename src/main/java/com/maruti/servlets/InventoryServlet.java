package com.maruti.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import com.maruti.dao.ProductDAO;
import com.maruti.models.Product;

public class InventoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        HttpSession session=request.getSession();
        Object userObj=session.getAttribute("loggedUser");
        if(userObj==null){ response.sendRedirect("login.jsp"); return; }
        int userId=((com.maruti.models.User)userObj).getUserId();

        ProductDAO dao=new ProductDAO();
        List<Product> list=dao.getAllProducts(userId);
        request.setAttribute("products", list);
        RequestDispatcher rd=request.getRequestDispatcher("inventory.jsp");
        rd.forward(request,response);
    }
}
