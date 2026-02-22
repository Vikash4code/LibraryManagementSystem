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

        User user = userDAO.validateUser(email, password);

        if (user != null) {

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if (user.getRole().equals("ADMIN")) {
                response.sendRedirect("admin/adminDashboard.jsp");
            } else {
                response.sendRedirect("student/studentDashboard.jsp");
            }

        } else {
            response.sendRedirect("login.jsp?error=invalid");
        }
    }
}