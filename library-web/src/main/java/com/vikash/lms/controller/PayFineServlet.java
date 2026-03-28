package com.vikash.lms.controller;

import com.vikash.lms.dao.FineDAO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/payFine")
public class PayFineServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int fineId = Integer.parseInt(request.getParameter("fineId"));

            FineDAO dao = new FineDAO();
            boolean success = dao.markFineAsPaid(fineId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/fines?message=Fine marked as paid successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/fines?error=Failed to mark fine as paid");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/fines?error=Invalid fine ID");
        }
    }
}