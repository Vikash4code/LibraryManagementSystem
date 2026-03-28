package com.vikash.lms.controller;

import com.vikash.lms.dao.TransactionDAO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/returnBook")
public class ReturnBookServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) 
            throws ServletException, IOException {

        try {
            int transactionId = Integer.parseInt(request.getParameter("transactionId"));

            TransactionDAO dao = new TransactionDAO();
            boolean success = dao.returnBook(transactionId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/transactions?message=Book returned successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/transactions?error=Failed to return book");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/transactions?error=Invalid transaction ID");
        }
    }
}