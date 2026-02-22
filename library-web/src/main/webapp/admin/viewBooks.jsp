<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ page import="java.util.List" %>
        <%@ page import="com.vikash.lms.model.Book" %>

            <!DOCTYPE html>
            <html>

            <head>
                <title>All Books</title>
            </head>

            <body>

                <h2>All Books</h2>

                <% List<Book> books = (List<Book>) request.getAttribute("books");

                        if (books != null) {
                        for (Book b : books) {
                        %>
                        <p>
                            ID: <%= b.getId() %> |
                                Title: <%= b.getTitle() %> |
                                    Author: <%= b.getAuthor() %> |
                                        ISBN: <%= b.getIsbn() %> |
                                            Category: <%= b.getCategory() %> |
                                                Total: <%= b.getTotalCopies() %> |
                                                    Available: <%= b.getAvailableCopies() %>
                        </p>
                        <% } } %>

                            <br>
                            <a href="addBook.jsp">Add Another Book</a>

            </body>

            </html>