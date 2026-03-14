package com.vikash.lms.controller;

import com.vikash.lms.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(contextPath.length());

        if (path.equals("/login.jsp") ||
                path.equals("/login") ||
                path.equals("/logout")) {

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {

            res.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (path.startsWith("/admin/") ||
                path.equals("/addBook") ||
                path.equals("/deleteBook") ||
                path.equals("/editBook") ||
                path.equals("/issueBook") ||
                path.equals("/returnBook") ||
                path.equals("/fines") ||
                path.equals("/analytics")) {

            if (!user.getRole().equals("ADMIN")) {
                res.sendRedirect(contextPath + "/login.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}