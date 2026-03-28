package com.vikash.lms.controller;

import com.vikash.lms.dao.TransactionDAO;
import com.vikash.lms.model.Transaction;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) 
            throws ServletException, IOException {

        TransactionDAO dao = new TransactionDAO();

        List<Transaction> list = dao.getAllTransactions();

        request.setAttribute("transactions", list);

        request.getRequestDispatcher("/admin/transactions.jsp")
               .forward(request, response);
    }
}