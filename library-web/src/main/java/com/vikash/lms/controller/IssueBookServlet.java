package com.vikash.lms.controller;

import com.vikash.lms.dao.TransactionDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/issueBook")
public class IssueBookServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int userId = Integer.parseInt(request.getParameter("userId")); 
            int bookId = Integer.parseInt(request.getParameter("bookId"));

            TransactionDAO dao = new TransactionDAO();
            boolean success = dao.issueBook(userId, bookId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/transactions?message=Book issued successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/issueBookPage?error=Issue failed. Book unavailable or already issued.");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/issueBookPage?error=Invalid user or book ID");
        }
    }
}