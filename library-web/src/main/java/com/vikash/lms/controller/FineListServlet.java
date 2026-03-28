package com.vikash.lms.controller;

import com.vikash.lms.dao.FineDAO;
import com.vikash.lms.model.Fine;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/fines")
public class FineListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        FineDAO dao = new FineDAO();

        // Calculate fines for overdue books first
        dao.calculateFines();
 
        // Get all fines
        List<Fine> fines = dao.getAllFines();

        request.setAttribute("fines", fines);

        request.getRequestDispatcher("/admin/fines.jsp")
                .forward(request, response);
    }
}