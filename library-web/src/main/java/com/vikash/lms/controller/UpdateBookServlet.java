package com.vikash.lms.controller;

import java.io.IOException;

import com.vikash.lms.dao.BookDAO;
import com.vikash.lms.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateBook")
public class UpdateBookServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException { 

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String category = request.getParameter("category");

        int totalCopies =
        Integer.parseInt(request.getParameter("totalCopies"));

        int availableCopies =
        Integer.parseInt(request.getParameter("availableCopies"));

        Book book = new Book();

        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setCategory(category);
        book.setTotalCopies(totalCopies);
        book.setAvailableCopies(availableCopies);

        BookDAO dao = new BookDAO();

        dao.updateBook(book);

        response.sendRedirect(request.getContextPath()+"/books");
    }
}