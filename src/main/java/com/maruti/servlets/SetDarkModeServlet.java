package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.maruti.dao.SettingsDAO;
import com.maruti.models.Settings;
import com.maruti.models.User;

@WebServlet("/SetDarkModeServlet")
public class SetDarkModeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User u = (User) session.getAttribute("loggedUser");

        // =========================
        // READ DARK MODE VALUE
        // =========================
        String darkParam = request.getParameter("darkMode");
        char darkMode = (darkParam != null && darkParam.equals("Y")) ? 'Y' : 'N';

        // =========================
        // UPDATE DATABASE
        // =========================
        Settings s = new Settings();
        s.setUserId(u.getUserId());
        s.setDarkMode(darkMode);

        SettingsDAO dao = new SettingsDAO();
        dao.updateDarkMode(s);

        // =========================
        // STORE IN SESSION (IMPORTANT)
        // =========================
        session.setAttribute("darkMode", darkMode);

        // =========================
        // STAY ON SAME PAGE
        // =========================
        response.sendRedirect("settings.jsp");
    }
}
