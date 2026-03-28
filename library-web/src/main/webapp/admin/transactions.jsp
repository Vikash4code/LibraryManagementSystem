<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.vikash.lms.model.Transaction" %>
<%@ include file="/common/header.jsp" %>


<style>
    .card {
        background: #ffffff;
        border-radius: 12px;
        padding: 20px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        margin: 20px;
    }

    .page-title {
        font-size: 24px;
        font-weight: 600;
        margin-bottom: 15px;
        color: #333;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        font-size: 14px;
    }
 
    thead {
        background: #4CAF50;
        color: white;
    }

    th, td {
        padding: 12px;
        text-align: center;
    }

    th {
        font-weight: 600;
        letter-spacing: 0.5px;
    }

    tbody tr {
        border-bottom: 1px solid #eee;
        transition: background 0.2s ease;
    }

    tbody tr:nth-child(even) {
        background: #f9f9f9;
    }

    tbody tr:hover {
        background: #f1f7ff;
    }

    .status {
        padding: 5px 10px;
        border-radius: 20px;
        font-size: 12px;
        font-weight: bold;
        display: inline-block;
    }

    .issued {
        background-color: #fff3cd;
        color: #856404;
    }

    .returned {
        background-color: #d4edda;
        color: #155724;
    }

    .overdue {
        background-color: #f8d7da;
        color: #721c24;
    }

    /* Responsive */
    @media (max-width: 768px) {
        table, thead, tbody, th, td, tr {
            display: block;
        }

        thead {
            display: none;
        }

        tr {
            margin-bottom: 15px;
            background: #fff;
            padding: 10px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        td {
            text-align: left;
            padding: 8px;
        }

        td::before {
            font-weight: bold;
            display: block;
            margin-bottom: 4px;
            color: #555;
        }

        td:nth-child(1)::before { content: "ID"; }
        td:nth-child(2)::before { content: "User ID"; }
        td:nth-child(3)::before { content: "Book ID"; }
        td:nth-child(4)::before { content: "Issue Date"; }
        td:nth-child(5)::before { content: "Due Date"; }
        td:nth-child(6)::before { content: "Status"; }
        td:nth-child(7)::before { content: "Action"; }
    }
</style>

<div class="card">
    <h2 class="page-title">📚 Transactions</h2>

    <% String message = request.getParameter("message");
       String error = request.getParameter("error");
       if (message != null) { %>
        <div style="color: green; margin-bottom: 10px;"><%= message %></div>
    <% } else if (error != null) { %>
        <div style="color: red; margin-bottom: 10px;"><%= error %></div>
    <% } %>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Book ID</th>
                <th>Issue Date</th>
                <th>Due Date</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<Transaction> list = (List<Transaction>) request.getAttribute("transactions");

            if (list != null && !list.isEmpty()) {
                for (Transaction t : list) {

                    String statusClass = "";
                    String status = t.getStatus() != null ? t.getStatus().toLowerCase() : "unknown";

                    if (status.contains("issued")) statusClass = "issued";
                    else if (status.contains("returned")) statusClass = "returned";
                    else if (status.contains("overdue")) statusClass = "overdue";
            %>
            <tr>
                <td><%= t.getId() %></td>
                <td><%= t.getUserId() %></td>
                <td><%= t.getBookId() %></td>
                <td><%= t.getIssueDate() != null ? t.getIssueDate() : "N/A" %></td>
                <td><%= t.getDueDate() != null ? t.getDueDate() : "N/A" %></td>
                <td>
                    <span class="status <%=statusClass%>">
                        <%= t.getStatus() != null ? t.getStatus() : "UNKNOWN" %>
                    </span>
                </td>
                <td>
                    <% if ("ISSUED".equalsIgnoreCase(t.getStatus())) { %>
                        <form action="<%= request.getContextPath() %>/returnBook" method="post" style="display: inline;">
                            <input type="hidden" name="transactionId" value="<%= t.getId() %>">
                            <button type="submit" style="background: #2196F3; color: white; border: none; padding: 5px 10px; border-radius: 3px; cursor: pointer;">Return Book</button>
                        </form>
                    <% } else { %>
                        <span style="color: #666;">-</span>
                    <% } %>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="7">No transactions found.</td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<%@ include file="/common/footer.jsp" %>