package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.maruti.dao.UserDAO;
import com.maruti.models.OTPUtil;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mobile = request.getParameter("mobile");
        UserDAO dao = new UserDAO();

        if(!dao.checkMobileExists(mobile)){
            response.getWriter().println(
                "<script>alert('Mobile not registered');window.location='forgot-password.jsp';</script>"
            );
            return;
        }

        HttpSession session = request.getSession();
        String otp = OTPUtil.generateOTP();

        session.setAttribute("resetMobile", mobile);
        session.setAttribute("otp", otp);

        System.out.println("Password Reset OTP for " + mobile + " = " + otp);

        response.sendRedirect("reset-otp.jsp");
    }
}
