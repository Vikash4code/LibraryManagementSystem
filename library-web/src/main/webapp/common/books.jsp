<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.vikash.lms.model.Book" %>
<%@ page import="com.vikash.lms.model.User" %>

<%
    User user = (User) session.getAttribute("user");
    List<Book> books = (List<Book>) request.getAttribute("books");
%>

<h2>All Books</h2>

<table border="1">
<tr>
    <th>ID</th>
    <th>Title</th>
    <th>Author</th>
    <th>ISBN</th>
    <th>Category</th>
    <th>Total</th>
    <th>Available</th>

    <% if (user.getRole().equals("ADMIN")) { %>
        <th>Actions</th>
    <% } %>
</tr>

<%
    for (Book b : books) {
%>
<tr>
    <td><%= b.getId() %></td>
    <td><%= b.getTitle() %></td>
    <td><%= b.getAuthor() %></td>
    <td><%= b.getIsbn() %></td>
    <td><%= b.getCategory() %></td>
    <td><%= b.getTotalCopies() %></td>
    <td><%= b.getAvailableCopies() %></td>

    <% if (user.getRole().equals("ADMIN")) { %>
    <td>
        <a href="deleteBook?id=<%= b.getId() %>">Delete</a>
    </td>
    <% } %>
</tr>
<%
    }
%>
</table>

<br>

<% if (user.getRole().equals("ADMIN")) { %>
    <a href="<%= request.getContextPath() %>/admin/addBook.jsp">
        Add New Book
    </a>
<% } %>
