package com.vikash.lms.controller;

import com.vikash.lms.dao.BookDAO;
import com.vikash.lms.model.Book;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class BookListServlet extends HttpServlet {

    private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("books", books);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/common/books.jsp");

        dispatcher.forward(request, response);
    }
}
