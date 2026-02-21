package com.vikash.lms.controller;

import com.vikash.lms.dao.BookDAO;
import com.vikash.lms.model.Book;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/addBook")
public class BookServlet extends HttpServlet {

    private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String category = request.getParameter("category");
        int totalCopies = Integer.parseInt(request.getParameter("totalCopies"));
        int availableCopies = Integer.parseInt(request.getParameter("availableCopies"));

        Book book = new Book(title, author, isbn, category, totalCopies, availableCopies);

        bookDAO.addBook(book);

        List<Book> books = bookDAO.getAllBooks();

        request.setAttribute("books", books);

        RequestDispatcher dispatcher = request.getRequestDispatcher("viewBooks.jsp");
        dispatcher.forward(request, response);
    }
}