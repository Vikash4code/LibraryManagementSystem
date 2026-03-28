package com.vikash.lms.controller;

import com.vikash.lms.dao.BookDAO;
import com.vikash.lms.model.Book;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addBook")
public class BookServlet extends HttpServlet {

    private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
 
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String category = request.getParameter("category");
        int totalCopies = Integer.parseInt(request.getParameter("totalCopies"));
        int availableCopies = Integer.parseInt(request.getParameter("availableCopies"));

        Book book = new Book(0, title, author, isbn, category, totalCopies, availableCopies);

        bookDAO.addBook(book);

        // IMPORTANT: Redirect to servlet, NOT JSP
        response.sendRedirect(request.getContextPath() + "/books");
    }
}