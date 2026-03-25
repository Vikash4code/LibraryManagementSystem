<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.vikash.lms.model.Book" %>
<%@ include file="/common/header.jsp" %>
<div class="card">
    <h2 class="page-title">All Books</h2>
    <table>
        <thead>
            <tr><th>ID</th><th>Title</th><th>Author</th><th>ISBN</th><th>Category</th><th>Total</th><th>Available</th></tr>
        </thead>
        <tbody>
            <% List<Book> books = (List<Book>) request.getAttribute("books");
               if (books != null) {
                   for (Book b : books) { %>
            <tr>
                <td><%= b.getId() %></td>
                <td><%= b.getTitle() %></td>
                <td><%= b.getAuthor() %></td>
                <td><%= b.getIsbn() %></td>
                <td><%= b.getCategory() %></td>
                <td><%= b.getTotalCopies() %></td>
                <td><%= b.getAvailableCopies() %></td>
            </tr>
            <%    }
               }
            %>
        </tbody>
    </table>
    <div class="form-controls" style="margin-top: 1rem;"><a class="primary" style="display:inline-block;text-decoration:none;" href="addBook.jsp">Add Another Book</a></div>
</div>
<%@ include file="/common/footer.jsp" %>