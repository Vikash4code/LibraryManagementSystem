package com.vikash.lms.controller;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.vikash.lms.dao.BookDAO;
import com.vikash.lms.model.Book;

@WebServlet("/editBook")
public class EditBookServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        BookDAO dao = new BookDAO();

        Book book = dao.getBookById(id);

        request.setAttribute("book", book);

        request.getRequestDispatcher("/admin/editBook.jsp")
               .forward(request,response);
    }
}