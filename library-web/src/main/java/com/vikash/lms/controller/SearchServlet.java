package com.vikash.lms.controller;

import com.vikash.lms.dao.BookDAO;
import com.vikash.lms.dao.UserDAO;
import com.vikash.lms.model.Book;
import com.vikash.lms.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/searchData")
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type");
        String query = request.getParameter("query");

        if (type == null) {
            out.println("[]");
            return;
        }

        if (query == null) {
            query = "";
        }
 
        StringBuilder json = new StringBuilder("[");

        try {
            if ("student".equalsIgnoreCase(type)) {
                UserDAO userDAO = new UserDAO();
                List<User> students = userDAO.searchStudents(query);

                int count = 0;
                for (User s : students) {
                    if (count > 0)
                        json.append(",");
                    json.append("{")
                        .append("\"id\":").append(s.getId()).append(",")
                        .append("\"name\":\"").append(s.getName().replace("\"", "\\\"")).append("\"")
                        .append("}");
                    count++;
                }
            } else if ("book".equalsIgnoreCase(type)) {
                BookDAO bookDAO = new BookDAO();
                List<Book> books = bookDAO.searchAvailableBooks(query);

                int count = 0;
                for (Book b : books) {
                    if (count > 0)
                        json.append(",");
                    json.append("{")
                        .append("\"id\":").append(b.getId()).append(",")
                        .append("\"title\":\"").append(b.getTitle().replace("\"", "\\\"")).append("\",")
                        .append("\"isbn\":\"").append(b.getIsbn() != null ? b.getIsbn() : "").append("\",")
                        .append("\"author\":\"").append(b.getAuthor() != null ? b.getAuthor() : "").append("\",")
                        .append("\"availableCopies\":").append(b.getAvailableCopies())
                        .append("}");
                    count++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");
        out.println(json.toString());
        out.close();
    }
}