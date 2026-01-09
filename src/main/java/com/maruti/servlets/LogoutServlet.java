package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); 
        if (session != null) {
            session.invalidate();
        }

        // Check if exit param, if yes redirect to index.jsp (app lock) else login.jsp
        String exit = request.getParameter("exit");
        if("true".equals(exit)){
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
