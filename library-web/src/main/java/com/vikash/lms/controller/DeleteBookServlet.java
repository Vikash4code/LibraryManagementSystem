package com.vikash.lms.controller;

import com.vikash.lms.dao.BookDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {

    private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        bookDAO.deleteBook(id);

        response.sendRedirect("books");
    }
}
