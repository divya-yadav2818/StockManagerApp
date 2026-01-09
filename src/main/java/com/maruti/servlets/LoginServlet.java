package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.maruti.dao.UserDAO;
import com.maruti.models.OTPUtil;
import com.maruti.util.EmailService;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Read the email parameter from form
        String username = request.getParameter("email"); // email input from login.jsp
        UserDAO dao = new UserDAO();

        // Check if email exists in database
        if (!dao.checkEmailExists(username)) {
            response.getWriter().println(
                "<script>alert('Email not registered'); window.location='login.jsp';</script>"
            );
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginEmail", username);

        // Generate OTP
        String otp = OTPUtil.generateOTP();
        session.setAttribute("otp", otp);

        // Send OTP via email
        boolean sent = EmailService.sendOTPEmail(username, otp);
        if (!sent) {
            response.getWriter().println(
                "<script>alert('Failed to send OTP email'); window.location='login.jsp';</script>"
            );
            return;
        }

        // Redirect to OTP page with email
        response.sendRedirect("otp.jsp?email=" + username);
    }
}
