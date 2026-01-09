package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.maruti.dao.UserDAO;
import com.maruti.models.User;

@WebServlet("/OTPServlet")
public class OTPServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String enteredOtp = request.getParameter("otp");
        HttpSession session = request.getSession();
        String sessionOtp = (String) session.getAttribute("otp");

        if (enteredOtp != null && enteredOtp.equals(sessionOtp)) {

            UserDAO dao = new UserDAO();
            User u = null;

            // ===== SIGNUP FLOW =====
            String signupEmail = (String) session.getAttribute("signupEmail");

            if (signupEmail != null) {

                String firstName = (String) session.getAttribute("firstName");
                String lastName = (String) session.getAttribute("lastName");
                String mobile = (String) session.getAttribute("mobile");

                u = dao.getUserByEmail(signupEmail);

                if (u == null) {
                    u = new User();
                    u.setUsername(signupEmail);   // EMAIL STORED AS USERNAME
                    u.setMobile(mobile);
                    u.setFirstName(firstName);
                    u.setLastName(lastName);
                    u.setPassword("1234"); // unchanged
                    dao.addUser(u);
                }

                session.setAttribute("loggedUser", u);

                // clear signup session
                session.removeAttribute("signupEmail");
                session.removeAttribute("firstName");
                session.removeAttribute("lastName");
                session.removeAttribute("mobile");

            } 
            // ===== LOGIN FLOW =====
            else {
                String loginEmail = (String) session.getAttribute("loginEmail");
                if (loginEmail != null) {
                    u = dao.getUserByEmail(loginEmail);
                    session.setAttribute("loggedUser", u);
                }
            }

            session.removeAttribute("otp");
            response.sendRedirect("index.jsp");

        } else {
            response.getWriter().println(
                "<script>alert('OTP incorrect'); window.location='otp.jsp';</script>"
            );
        }
    }
}
