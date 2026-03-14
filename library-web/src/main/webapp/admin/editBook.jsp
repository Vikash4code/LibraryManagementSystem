<%@ page import="com.vikash.lms.model.Book" %>

<%
Book book = (Book)request.getAttribute("book");
%>

<h2>Edit Book</h2>

<form action="<%=request.getContextPath()%>/updateBook" method="post">

<input type="hidden" name="id" value="<%=book.getId()%>">

Title:<br>
<input type="text" name="title" value="<%=book.getTitle()%>"><br><br>

Author:<br>
<input type="text" name="author" value="<%=book.getAuthor()%>"><br><br>

ISBN:<br>
<input type="text" name="isbn" value="<%=book.getIsbn()%>"><br><br>

Category:<br>
<input type="text" name="category" value="<%=book.getCategory()%>"><br><br>

Total Copies:<br>
<input type="number" name="totalCopies" value="<%=book.getTotalCopies()%>"><br><br>

Available Copies:<br>
<input type="number" name="availableCopies" value="<%=book.getAvailableCopies()%>"><br><br>

<button type="submit">Update Book</button>

</form>