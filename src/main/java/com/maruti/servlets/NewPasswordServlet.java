package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.maruti.dao.UserDAO;
import com.maruti.models.User;

@WebServlet("/NewPasswordServlet")
public class NewPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String mobile = (String) session.getAttribute("resetMobile");
        String password = request.getParameter("password");

        if(mobile == null || password == null || password.trim().isEmpty()){
            response.sendRedirect("forgot-password.jsp");
            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.getUserByMobile(mobile);

        if(user != null){
            user.setPassword(password);
            dao.updatePassword(user);

            // ✅ Set updated user in session
            session.setAttribute("loggedUser", user);
            session.removeAttribute("resetMobile");

            // Redirect to index.jsp to enter password
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("forgot-password.jsp");
        }
    }
}
