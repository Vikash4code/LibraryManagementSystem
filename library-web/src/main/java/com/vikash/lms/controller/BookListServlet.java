package com.vikash.lms.controller;

import com.vikash.lms.dao.BookDAO;
import com.vikash.lms.model.Book;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({"/books", "/student/books"}) 
public class BookListServlet extends HttpServlet {

    private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");

        List<Book> books;

        if (query != null && !query.trim().isEmpty()) {
            books = bookDAO.searchBooks(query);
        } else {
            books = bookDAO.getAllBooks();
        }

        request.setAttribute("books", books);

        request.getRequestDispatcher("/books.jsp")
                .forward(request, response);
    }
}