package com.vikash.lms.controller;

import com.vikash.lms.dao.UserDAO;
import com.vikash.lms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (name == null || email == null || password == null ||
                name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/register.jsp?error=invalid");
            return;
        }

        User user = new User(name.trim(), email.trim(), password.trim(), "STUDENT");

        boolean created = userDAO.addStudent(user);
        if (created) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?message=registered");
        } else {
            response.sendRedirect(request.getContextPath() + "/register.jsp?error=duplicate");
        }
    }
}
