<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.vikash.lms.model.Book" %>
<%@ page import="com.vikash.lms.model.User" %>

<%
User user = (User) session.getAttribute("user");
String role = (user != null) ? user.getRole() : "GUEST";
List<Book> books = (List<Book>) request.getAttribute("books");
%>

<h2>All Books</h2>

<form action="<%= request.getContextPath() %>/books" method="get">
Search:
<input type="text" name="query">
<input type="submit" value="Search">
</form>
 
<br>

<table border="1">

<tr>
<th>ID</th>
<th>Title</th>
<th>Author</th>
<th>ISBN</th>
<th>Category</th>
<th>Total</th>
<th>Available</th>

<% if("ADMIN".equals(role)){ %>
<th>Actions</th>
<% } %>

</tr>

<%
for(Book b : books){
%>

<tr>

<td><%= b.getId() %></td>
<td><%= b.getTitle() %></td>
<td><%= b.getAuthor() %></td>
<td><%= b.getIsbn() %></td>
<td><%= b.getCategory() %></td>
<td><%= b.getTotalCopies() %></td>
<td><%= b.getAvailableCopies() %></td>

<% if ("ADMIN".equals(role)) { %>

<td>

<a href="<%= request.getContextPath() %>/editBook?id=<%= b.getId() %>">
Edit
</a>

|

<a href="<%= request.getContextPath() %>/deleteBook?id=<%= b.getId() %>">
Delete
</a>

</td>

<% } %>

</tr>

<%
}
%>

</table>

<br>

<% if("ADMIN".equals(role)){ %>

<a href="<%= request.getContextPath() %>/admin/addBook.jsp">
Add New Book
</a>

<% } %>