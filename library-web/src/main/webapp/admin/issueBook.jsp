<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*" %>
        <%@ page import="com.vikash.lms.model.User" %>
            <%@ page import="com.vikash.lms.model.Book" %>
                <%@ include file="/common/header.jsp" %>

                    <style>
                        body {
                            background: #f4f6f9;
                            font-family: 'Segoe UI', sans-serif;
                        }

                        .card {
                            max-width: 600px;
                            margin: 40px auto;
                            background: #fff;
                            padding: 25px;
                            border-radius: 12px;
                            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
                        }

                        .page-title {
                            font-size: 24px;
                            font-weight: 600;
                            margin-bottom: 20px;
                            color: #333;
                            text-align: center;
                        }

                        .form-group {
                            margin-bottom: 18px;
                        }

                        label {
                            display: block;
                            margin-bottom: 6px;
                            font-weight: 500;
                            color: #444;
                        }

                        .form-select {
                            width: 100%;
                            padding: 10px;
                            border-radius: 8px;
                            border: 1px solid #ccc;
                            font-size: 14px;
                            background: #fff;
                            transition: 0.2s;
                        }

                        .form-select:focus {
                            border-color: #4CAF50;
                            outline: none;
                        }

                        .btn {
                            width: 100%;
                            padding: 12px;
                            background: #4CAF50;
                            color: white;
                            border: none;
                            border-radius: 8px;
                            font-size: 16px;
                            font-weight: 500;
                            cursor: pointer;
                            transition: 0.3s;
                        }

                        .btn:hover {
                            background: #45a049;
                        }
                    </style>

                    <div class="card">
                        <h2 class="page-title">📚 Issue Book</h2>

                        <form action="<%=request.getContextPath()%>/issueBook" method="post">

                            <!-- Student Dropdown -->
                            <div class="form-group">
                                <label>Select Student</label>
                                <select class="form-select" name="userId" required>
                                    <option value="" disabled selected>-- Choose Student --</option>
                                    <% List<User> students = (List<User>) request.getAttribute("students");
                                            if (students != null) {
                                            for (User s : students) {
                                            %>
                                            <option value="<%=s.getId()%>">
                                                <%=s.getName()%>
                                            </option>
                                            <% } } %>
                                </select>
                            </div>

                            <!-- Book Dropdown -->
                            <div class="form-group">
                                <label>Select Book</label>
                                <select class="form-select" name="bookId" required>
                                    <option value="" disabled selected>-- Choose Book --</option>
                                    <% List<Book> books = (List<Book>) request.getAttribute("books");
                                            if (books != null) {
                                            for (Book b : books) {
                                            %>
                                            <option value="<%=b.getId()%>">
                                                <%=b.getTitle()%>
                                            </option>
                                            <% } } %>
                                </select>
                            </div>

                            <!-- Submit -->
                            <button class="btn" type="submit">📖 Issue Book</button>

                        </form>
                    </div>

                    <%@ include file="/common/footer.jsp" %>