package com.vikash.lms.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/") // 🔥 ROOT URL HANDLER
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Always allow homepage
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}