package com.vikash.lms.controller;

import com.vikash.lms.model.Book;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addBook")
public class BookServlet extends HttpServlet {

    private static final List<Book> bookList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");

        Book book = new Book(id, title, author);
        bookList.add(book);

        request.setAttribute("message", "Book Added Successfully!");
        request.setAttribute("books", bookList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("bookSuccess.jsp");
        dispatcher.forward(request, response);
    }
}
