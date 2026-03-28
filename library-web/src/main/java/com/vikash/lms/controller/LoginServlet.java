package com.vikash.lms.controller;

import com.vikash.lms.dao.UserDAO;
import com.vikash.lms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (email != null) {
            email = email.trim();
        }
        if (password != null) {
            password = password.trim();
        }

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=invalid");
            return;
        }

        User user = userDAO.validateUser(email, password);

        if (user != null) {
            if (role == null || role.isEmpty()) {
                response.sendRedirect("login.jsp?error=role");
                return;
            }

            if (!user.getRole().equalsIgnoreCase(role)) {
                response.sendRedirect("login.jsp?error=wrongrole&selected=" + role);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            if (user.getRole().equalsIgnoreCase("ADMIN")) {
                response.sendRedirect("admin/adminDashboard.jsp");
            } else {
                response.sendRedirect("student/studentDashboard.jsp");
            }

        } else {
            response.sendRedirect("login.jsp?error=invalid");
        }
    }
} 