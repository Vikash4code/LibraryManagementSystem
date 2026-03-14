<h2>Admin Dashboard</h2>

<ul>

    <li>
        <a href="<%= request.getContextPath() %>/books">
            Manage Books
        </a>
    </li>

    <li>
        <a href="<%= request.getContextPath() %>/admin/addBook.jsp">
            Add Book
        </a>
    </li>

    <li>
        <a href="<%=request.getContextPath()%>/issueBookPage">
            Issue Book
        </a>
    </li>


    <li>
        <a href="<%=request.getContextPath()%>/admin/addStudent.jsp">Add Student</a>
    </li>
    <li>
        <a href="<%=request.getContextPath()%>/students">View Students</a>
    </li>
    <li>
        <a href="<%= request.getContextPath() %>/logout">
            Logout
        </a>
    </li>

</ul>