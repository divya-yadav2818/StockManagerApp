package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.maruti.dao.UserDAO;
import com.maruti.models.OTPUtil;
import com.maruti.util.EmailService;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String mobile    = request.getParameter("mobile");
        String email     = request.getParameter("email"); // EMAIL

        UserDAO dao = new UserDAO();

        // Check if mobile already registered
        if (dao.checkMobileExists(mobile)) {
            response.getWriter().println(
                "<script>alert('Mobile number already registered'); window.location='signup.jsp';</script>"
            );
            return;
        }

        // Check if email already registered
        if (dao.checkEmailExists(email)) {
            response.getWriter().println(
                "<script>alert('Email already registered. Try another email'); window.location='signup.jsp';</script>"
            );
            return;
        }

        // Store signup info in session
        HttpSession session = request.getSession();
        session.setAttribute("signupMobile", mobile);
        session.setAttribute("signupEmail", email);
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);

        // Generate OTP
        String otp = OTPUtil.generateOTP();
        session.setAttribute("otp", otp);

        // Send OTP via Email
        boolean sent = EmailService.sendOTPEmail(email, otp);
        if (!sent) {
            response.getWriter().println(
                "<script>alert('Failed to send OTP email. Check your email or App Password'); window.location='signup.jsp';</script>"
            );
            return;
        }

        // Redirect to OTP page (EMAIL based)
        response.sendRedirect("otp.jsp?email=" + email);
    }
}
