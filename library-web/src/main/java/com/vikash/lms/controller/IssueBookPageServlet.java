package com.vikash.lms.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.vikash.lms.dao.BookDAO;
import com.vikash.lms.dao.UserDAO;

@WebServlet("/issueBookPage")
public class IssueBookPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response)
                         throws ServletException, IOException {

        UserDAO userDAO = new UserDAO();
        BookDAO bookDAO = new BookDAO();

        request.setAttribute("students", userDAO.getAllStudents());
        request.setAttribute("books", bookDAO.getAvailableBooks());

        request.getRequestDispatcher("/admin/issueBook.jsp")
               .forward(request, response);
    }
}