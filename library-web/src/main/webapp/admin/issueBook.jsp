<%@ page import="java.util.*" %>
<%@ page import="com.vikash.lms.model.User" %>
<%@ page import="com.vikash.lms.model.Book" %>

<h2>Issue Book</h2>

<form action="<%=request.getContextPath()%>/issueBook" method="post">

Student:

<select name="userId">

<%
List<User> students = (List<User>)request.getAttribute("students");

for(User s : students){
%>

<option value="<%=s.getId()%>">
<%=s.getName()%> - <%=s.getEmail()%>
</option>

<%
}
%>

</select>

<br><br>

Book:

<select name="bookId">

<%
List<Book> books = (List<Book>)request.getAttribute("books");

for(Book b : books){
%>

<option value="<%=b.getId()%>">
<%=b.getTitle()%> (ISBN:<%=b.getIsbn()%>) Available:<%=b.getAvailableCopies()%>
</option>

<%
}
%>

</select>

<br><br>

<button type="submit">Issue Book</button>

</form>