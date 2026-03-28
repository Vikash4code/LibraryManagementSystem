<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/header.jsp" %>
<div class="card">
    <h2 class="page-title">Admin Dashboard</h2>
    <div class="form-grid">
        <a class="primary" style="text-align:center;display:block;text-decoration:none;" href="<%= request.getContextPath() %>/books">Manage Books</a>
        <a class="primary" style="text-align:center;display:block;text-decoration:none;" href="<%= request.getContextPath() %>/admin/addBook.jsp">Add Book</a>
        <a class="primary" style="text-align:center;display:block;text-decoration:none;" href="<%=request.getContextPath()%>/issueBookPage">Issue Book</a>
        <a class="primary" style="text-align:center;display:block;text-decoration:none;" href="<%=request.getContextPath()%>/admin/addStudent.jsp">Add Student</a>
        <a class="primary" style="text-align:center;display:block;text-decoration:none;" href="<%= request.getContextPath() %>/students">View Students</a>
        <a class="primary" style="text-align:center;display:block;text-decoration:none;" href="<%= request.getContextPath() %>/transactions">Transactions</a>
        <a class="primary" style="text-align:center;display:block;text-decoration:none;" href="<%= request.getContextPath() %>/fines">Manage Fines</a>
    </div>
</div>
<%@ include file="/common/footer.jsp" %> 