package com.vikash.lms.controller;

import com.vikash.lms.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(contextPath.length());

        // Public access
        if (path.equals("/login.jsp") ||
                path.equals("/login") ||
                path.equals("/logout") ||
                path.startsWith("/css") ||
                path.startsWith("/js")) {

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // ADMIN folder protection
        if (path.startsWith("/admin/") &&
                !user.getRole().equals("ADMIN")) {

            res.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        // STUDENT folder protection
        if (path.startsWith("/student/") &&
                !user.getRole().equals("STUDENT")) {

            res.sendRedirect(contextPath + "/login.jsp");
            return;
        }
        if (path.equals("/books")) {
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}
