package com.maruti.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.maruti.dao.SettingsDAO;
import com.maruti.dao.UserDAO;
import com.maruti.models.Settings;
import com.maruti.models.User;

@WebServlet("/SettingsServlet")
public class SettingsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        if(user == null){
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = user.getUserId();

        SettingsDAO dao = new SettingsDAO();
        Settings existingSettings = dao.getSettingsByUserId(userId);
        if(existingSettings == null){
            existingSettings = new Settings();
            existingSettings.setUserId(userId);
        }

        // =====================
        // PASSWORD UPDATE
        // =====================
        String password = request.getParameter("password");
        if(password != null && !password.trim().isEmpty()){
            user.setPassword(password);
            new UserDAO().updatePassword(user);
            session.setAttribute("loggedUser", user);
            existingSettings.setAppPassword(password); // optional: save in settings table if required
        }

        // =====================
        // DARK MODE
        // =====================
        String darkParam = request.getParameter("darkMode");
        char darkMode = (darkParam != null && darkParam.equals("Y")) ? 'Y' : 'N';
        existingSettings.setDarkMode(darkMode);

        // =====================
        // INSERT OR UPDATE SETTINGS
        // =====================
        if(dao.getSettingsByUserId(userId) == null){
            dao.insertSettings(existingSettings); // new user: insert row
        } else {
            dao.updateDarkMode(existingSettings); // existing user: update
        }

        // 🔥 store in session
        session.setAttribute("darkMode", darkMode);

        // stay on same page
        response.sendRedirect("settings.jsp");
    }
}
