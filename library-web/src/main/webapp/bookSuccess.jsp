<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.vikash.lms.model.Book" %>

<!DOCTYPE html>
<html>
<head>
    <title>Books</title>
</head>
<body>

<h2>${message}</h2>

<h3>Book List:</h3>

<%
    List<Book> books = (List<Book>) request.getAttribute("books");
    if (books != null) {
        for (Book b : books) {
%>
            <p>
                ID: <%= b.getId() %> |
                Title: <%= b.getTitle() %> |
                Author: <%= b.getAuthor() %>
            </p>
<%
        }
    }
%>

<br>
<a href="addBook.jsp">Add Another Book</a>

</body>
</html>
