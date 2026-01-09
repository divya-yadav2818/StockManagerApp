package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.maruti.models.OTPUtil;
import com.maruti.util.EmailService;

@WebServlet("/ResendOTPServlet")
public class ResendOTPServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String email = (String) session.getAttribute("signupEmail");
        if (email == null) {
            email = (String) session.getAttribute("loginEmail");
        }

        if (email != null) {
            String otp = OTPUtil.generateOTP();
            session.setAttribute("otp", otp);

            // ✅ use same method everywhere
            EmailService.sendOTPEmail(email, otp);

            response.getWriter().println(
                "<script>alert('OTP resent to " + email + "'); window.location='otp.jsp?email=" + email + "';</script>"
            );
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
