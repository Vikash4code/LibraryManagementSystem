<%@ page import="com.vikash.lms.model.Book" %>
<%
Book book = (Book)request.getAttribute("book");
%>
<%@ include file="/common/header.jsp" %>
<div class="card">
    <h2 class="page-title">Edit Book</h2>
    <form action="<%=request.getContextPath()%>/updateBook" method="post" class="form-grid">
        <input type="hidden" name="id" value="<%=book.getId()%>">
        <div><label>Title</label><input class="form-control" type="text" name="title" value="<%=book.getTitle()%>"></div>
        <div><label>Author</label><input class="form-control" type="text" name="author" value="<%=book.getAuthor()%>"></div>
        <div><label>ISBN</label><input class="form-control" type="text" name="isbn" value="<%=book.getIsbn()%>"></div>
        <div><label>Category</label><input class="form-control" type="text" name="category" value="<%=book.getCategory()%>"></div>
        <div><label>Total Copies</label><input class="form-control" type="number" name="totalCopies" value="<%=book.getTotalCopies()%>"></div>
        <div><label>Available Copies</label><input class="form-control" type="number" name="availableCopies" value="<%=book.getAvailableCopies()%>"></div>
        <div class="form-controls"><button class="primary" type="submit">Update Book</button></div>
    </form>
</div>
<%@ include file="/common/footer.jsp" %>