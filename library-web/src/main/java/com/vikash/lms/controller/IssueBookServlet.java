package com.vikash.lms.controller;

import com.vikash.lms.dao.TransactionDAO;
import com.vikash.lms.dao.BookDAO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/issueBook")
public class IssueBookServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        TransactionDAO dao = new TransactionDAO();

        boolean success = dao.issueBook(userId, bookId);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/transactions");
        } else {
            response.getWriter().println("Issue failed. Book unavailable or already issued.");
        }
    }
}